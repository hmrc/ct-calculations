/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.ct600.v2.calculations

import org.joda.time.LocalDate
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.CP2
import uk.gov.hmrc.ct.ct600.v2.B80
import uk.gov.hmrc.ct.ct600a._
import uk.gov.hmrc.ct.ct600a.v2._

class LoansToParticipatorsCalculatorSpec extends WordSpec with Matchers {

  def someDate(value:String):Option[LocalDate] = Some(new LocalDate(value))

  val lpq01Table = Table(
    ("lpq03", "lpq04", "lpq05", "expectedLpq01"),
    (Some(true), Some(true), None, true),
    (Some(true), Some(false), Some(true), true),
    (Some(true), Some(true), None, true),
    (Some(true), Some(false), Some(true), true),
    (None, None, None, false),
    (Some(false), None, None, false),
    (Some(true), Some(false), None, false)

  )

  "LoansToParticipatorsCalculator" should {
    "correctly validate LPQ01 " in new LoansToParticipatorsCalculator {
      forAll(lpq01Table) {
        (lpq03: Option[Boolean], lpq04: Option[Boolean], lpq05: Option[Boolean], expected: Boolean) => {
          calculateLPQ01(LPQ03(lpq03), LPQ04(lpq04), LPQ05(lpq05)) shouldBe LPQ01(expected)
        }
      }
    }

    val a2Table = Table(
      ("expectedValue", "lp02"),
      (1, LP02(Some(Loan(id = "123", name = "", amount = 1, repaid = false, lastRepaymentDate = None, totalAmountRepaid = None) :: Nil))),
      (6, LP02(Some(
          Loan(id = "123", name = "", amount = 1, repaid = true, lastRepaymentDate = Some(new LocalDate("1939-09-02")), totalAmountRepaid = Some(99)) ::
          Loan(id = "123", name = "", amount = 2, repaid = true, lastRepaymentDate = Some(new LocalDate("1939-08-30")), totalAmountRepaid = Some(99)) ::
          Loan(id = "123", name = "", amount = 3, repaid = false, lastRepaymentDate = None, totalAmountRepaid = None) :: Nil)))
    )

    "correctly calculate A2 using loans made during the accounting period and still outstanding at the end of the accounting period - this should be all in this filing" in new LoansToParticipatorsCalculator {
      forAll(a2Table) {
        (expectedValue: Int, lp02: LP02) => {
          calculateA2(lp02) shouldBe A2(Some(expectedValue))
        }
      }
    }

    "correctly calculate A3 as 25% of A2" in new LoansToParticipatorsCalculator {
      calculateA3(A2(Some(1))) shouldBe A3(Some(0.25))
      calculateA3(A2(Some(333))) shouldBe A3(Some(83.25))
    }


    val reliefDueNowOnLoanTable = Table(
        ("expectedValue", "isRepaid", "repaymentDate"),
        (true,           true,       someDate("2014-09-30")),
        (false,           true,       someDate("2014-10-01")),
        (false,           false,       None)
    )

    "correctly calculate whether relief is due now for loans repaid within 9 months of end of AP" in new LoansToParticipatorsCalculator {
      forAll(reliefDueNowOnLoanTable) {
        (expectedValue: Boolean, isRepaid:Boolean, repaymentDate:Option[LocalDate]) => {
          val aLoan = Loan(id = "123", name = "", amount = 10000, repaid = isRepaid, lastRepaymentDate = repaymentDate)
          val acctPeriodEnd = new LocalDate("2013-12-31")
          aLoan.isRepaymentReliefEarlierThanDue(acctPeriodEnd) shouldBe expectedValue
        }
      }
    }

    val reliefDueNowOnWriteOffTable = Table(
      ("expectedValue", "dateWrittenOff"),
      (true,            "2014-09-30"),
      (false,           "2014-10-01")
    )

    "correctly calculate whether relief is due now for write offs made within 9 months of end of AP" in new LoansToParticipatorsCalculator {
      forAll(reliefDueNowOnWriteOffTable) {
        (expectedValue: Boolean, dateWrittenOff: String) => {

          val writeOff = WriteOff(loanId = "123", amountWrittenOff = 10, dateWrittenOff = new LocalDate(dateWrittenOff), endDateOfWriteOffAP = Some(new LocalDate("2050-12-31")))
          val acctPeriodEnd = new LocalDate("2013-12-31")
          writeOff.isReliefEarlierThanDue(acctPeriodEnd) shouldBe expectedValue
        }
      }
    }

    val a4Table = Table(
      ("expectedValue", "lp02"),
      (None, LP02(Some(Loan(id = "123", name = "", amount = 123, repaid = true, lastRepaymentDate = Some(new LocalDate("1939-08-31")), totalAmountRepaid = Some(1)) :: Nil))),
      (Some(1), LP02(Some(Loan(id = "123", name = "", amount = 123, repaid = true, lastRepaymentDate = Some(new LocalDate("1939-09-01")), totalAmountRepaid = Some(1)) :: Nil))),
      (Some(1), LP02(Some(Loan(id = "123", name = "", amount = 123, repaid = true, lastRepaymentDate = Some(new LocalDate("1940-05-31")), totalAmountRepaid = Some(1)) :: Nil))),
      (None, LP02(Some(Loan(id = "123", name = "", amount = 123, repaid = true, lastRepaymentDate = Some(new LocalDate("1940-06-01")), totalAmountRepaid = Some(1)) :: Nil))),
      (None, LP02(Some(Loan(id = "123", name = "", amount = 123, repaid = false, lastRepaymentDate = Some(new LocalDate("1939-09-01")), totalAmountRepaid = Some(1)) :: Nil))),
      (Some(4), LP02(Some(
        Loan(id = "123", name = "", amount = 123, repaid = true, lastRepaymentDate = Some(new LocalDate("1939-09-02")), totalAmountRepaid = Some(1)) ::
        Loan(id = "456", name = "", amount = 456, repaid = true, lastRepaymentDate = Some(new LocalDate("1939-08-30")), totalAmountRepaid = Some(2)) ::
        Loan(id = "789", name = "", amount = 789, repaid = true, lastRepaymentDate = Some(new LocalDate("1940-05-31")), totalAmountRepaid = Some(3)) :: Nil)))
    )

    "correctly calculate A4 using loan repayments made between the end of the accounting period and 9months and 1 day later" in new LoansToParticipatorsCalculator {
      forAll(a4Table) {
        (expectedValue: Option[Int], lp02: LP02) => {
          val cp2 = CP2(new LocalDate("1939-08-31"))
          calculateA4(cp2, lp02) shouldBe A4(expectedValue)
        }
      }
    }


    val a5Table = Table(
      ("expectedValue", "lp03"),
      (None, LP03(Some(List(WriteOff("123", 1, new LocalDate("1939-08-31")))))),
      (Some(1), LP03(Some(List(WriteOff("123", 1, new LocalDate("1939-09-01")))))),
      (Some(1), LP03(Some(List(WriteOff("123", 1, new LocalDate("1940-05-31")))))),
      (None, LP03(Some(List(WriteOff("123", 1, new LocalDate("1940-06-01"), Some(new LocalDate("1940-08-31"))))))),
      (Some(4), LP03(Some(
        List(
          WriteOff("123", 1, new LocalDate("1939-09-01")),
          WriteOff("456", 2, new LocalDate("1940-06-02"), Some(new LocalDate("1940-08-31"))),
          WriteOff("789", 3, new LocalDate("1940-05-31")))
      )))
    )

    "correctly validate A5 using write offs made between the end of the accounting period and 9months and 1 day later" in new LoansToParticipatorsCalculator {
      forAll(a5Table) {
        (expectedValue: Option[Int], lp03: LP03) => {
          val cp2 = CP2(new LocalDate("1939-08-31"))
          calculateA5(cp2, lp03) shouldBe A5(expectedValue)
        }
      }
    }

    "correctly calculate A6 as A4+A5" in new LoansToParticipatorsCalculator {
      calculateA6(A4(Some(4)), A5(Some(5))) shouldBe A6(Some(9))
      calculateA6(A4(None), A5(Some(5))) shouldBe A6(Some(5))
      calculateA6(A4(Some(4)), A5(None)) shouldBe A6(Some(4))
    }

    "correctly calculate A7 as 25% of A6" in new LoansToParticipatorsCalculator {
      calculateA7(A6(Some(1))) shouldBe A7(Some(0.25))
      calculateA7(A6(Some(333))) shouldBe A7(Some(83.25))
    }


    val a8Table = Table(
      ("expectedValue", "lp02", "filingDate"),
      (None, LP02(Some(Loan(id = "123", name = "", amount = 123, repaid = true, lastRepaymentDate = Some(new LocalDate("1940-05-31")), totalAmountRepaid = Some(1), endDateOfRepaymentAP = someDate("1940-08-31")) :: Nil)), LPQ07(someDate("1941-03-01"))),
      (Some(1), LP02(Some(Loan(id = "456", name = "", amount = 123, repaid = true, lastRepaymentDate = Some(new LocalDate("1940-07-01")), totalAmountRepaid = Some(1), endDateOfRepaymentAP = someDate("1940-08-31")) :: Nil)), LPQ07(someDate("1941-06-01"))),
      (Some(4), LP02(Some(
        Loan(id = "123", name = "", amount = 123, repaid = true, lastRepaymentDate = Some(new LocalDate("1940-06-01")), totalAmountRepaid = Some(1), endDateOfRepaymentAP = someDate("1940-08-31")) ::
          Loan(id = "456", name = "", amount = 456, repaid = true, lastRepaymentDate = Some(new LocalDate("1940-05-31")), totalAmountRepaid = Some(2), endDateOfRepaymentAP = someDate("1940-08-31")) ::
          Loan(id = "789", name = "", amount = 789, repaid = true, lastRepaymentDate = Some(new LocalDate("1941-01-31")), totalAmountRepaid = Some(3), endDateOfRepaymentAP = someDate("1941-08-31")) :: Nil)), LPQ07(someDate("1942-06-01")))
    )
    "correctly calculate A8 using loan repayments made more than 9 months after the end of the accounting period " in new LoansToParticipatorsCalculator {
      forAll(a8Table) {
        (expectedValue: Option[Int], lp02: LP02, filingDate: LPQ07) => {
          val cp2 = CP2(new LocalDate("1939-08-31"))
          calculateA8(cp2, lp02, filingDate) shouldBe A8(expectedValue)
        }
      }
    }

    val A8InverseTable = Table(
      ("expectedValue", "lp02", "filingDate"),
      (0, LP02(Some(Loan(id = "123", name = "", amount = 123, repaid = true, lastRepaymentDate = Some(new LocalDate("2014-05-31")), totalAmountRepaid = Some(1), endDateOfRepaymentAP = someDate("2014-12-31")) :: Nil)), LPQ07(someDate("2015-06-01"))), //repayment too early
      (0, LP02(Some(Loan(id = "456", name = "", amount = 123, repaid = true, lastRepaymentDate = Some(new LocalDate("2014-06-01")), totalAmountRepaid = Some(1), endDateOfRepaymentAP = someDate("2014-12-31")) :: Nil)), LPQ07(someDate("2015-10-01"))), // relief due now
      (1, LP02(Some(Loan(id = "457", name = "", amount = 123, repaid = true, lastRepaymentDate = Some(new LocalDate("2014-10-01")), totalAmountRepaid = Some(1), endDateOfRepaymentAP = someDate("2014-12-31")) :: Nil)), LPQ07(someDate("2015-09-29"))), // filing date early - relief not yet due
      (1, LP02(Some(Loan(id = "123", name = "", amount = 123, repaid = true, lastRepaymentDate = Some(new LocalDate("2014-11-01")), totalAmountRepaid = Some(1), endDateOfRepaymentAP = someDate("2014-12-31")) :: Nil)), LPQ07(None)), // no filing date - meaning LPQ06 == true ie filied within 9 months
      (2, LP02(Some(
        Loan(id = "123", name = "", amount = 123, repaid = true, lastRepaymentDate = Some(new LocalDate("2014-05-31")), totalAmountRepaid = Some(1), endDateOfRepaymentAP = someDate("2014-12-31")) ::
          Loan(id = "456", name = "", amount = 456, repaid = true, lastRepaymentDate = Some(new LocalDate("2014-10-01")), totalAmountRepaid = Some(2), endDateOfRepaymentAP = someDate("2014-12-31")) ::
          Loan(id = "789", name = "", amount = 789, repaid = true, lastRepaymentDate = Some(new LocalDate("2014-06-01")), totalAmountRepaid = Some(5), endDateOfRepaymentAP = someDate("2014-12-31")) :: Nil)), LPQ07(someDate("2015-09-30")))
    )
    "correctly calculate A8Inverse using loan repayments made more than 9 months after the end of the accounting period " in new LoansToParticipatorsCalculator {
      forAll(A8InverseTable) {
        (expectedValue: Int, lp02: LP02, filingDate: LPQ07) => {
          val cp2 = CP2(new LocalDate("2013-12-31"))
          calculateA8Inverse(cp2, lp02, filingDate) shouldBe A8Inverse(Some(expectedValue))
        }
      }
    }
    

    val reliefLaterThanDueNowTable = Table(
      ("expectedValue", "isRepaid", "repaymentDate",        "endDateOfAccountingPeriodDuringWhichRepaymentWasMade",     "filingDate"),
      (false,           true,       someDate("2014-09-30"),     someDate("2014-12-31"),                                               someDate("2015-10-01")),
      (true,            true,       someDate("2014-10-01"),     someDate("2014-12-31"),                                               someDate("2015-10-01")),

      (false,           true,       someDate("2014-10-01"),     someDate("2014-12-31"),                                               someDate("2015-09-30")),
      (true,            true,       someDate("2014-10-01"),     someDate("2014-12-31"),                                               someDate("2015-10-01")),

      (false,           false,      None,                   someDate("2014-12-31"),                                               someDate("2015-10-01")),
      (false,           false,      someDate("2014-10-01"),     someDate("2014-12-31"),                                               someDate("2015-10-01")),
      (false,           true,       someDate("2014-10-01"),     someDate("2014-12-31"),                                               None),
      (false,            true,      someDate("2014-09-30"),     None,                                                             someDate("2015-10-01"))
    )
    "correctly calculate isRepaymentLaterReliefNowDue using loan repayments made more than 9 months after the end of the accounting period" in new LoansToParticipatorsCalculator {
      forAll(reliefLaterThanDueNowTable) {
        (expectedValue: Boolean, isRepaid:Boolean, repaymentDate:Option[LocalDate], endDateOfAccountingPeriodDuringWhichRepaymentWasMade: Option[LocalDate], filingDate: Option[LocalDate]) => {
          val aLoan = Loan(id = "123", name = "", amount = 10000, repaid = isRepaid, lastRepaymentDate = repaymentDate, totalAmountRepaid = Some(5000), endDateOfRepaymentAP = endDateOfAccountingPeriodDuringWhichRepaymentWasMade)
          val acctPeriodEnd = new LocalDate("2013-12-31")
          aLoan.isRepaymentLaterReliefNowDue(acctPeriodEnd, LPQ07(filingDate)) shouldBe expectedValue
        }
      }
    }
    "throw exception when repayment date is missing" in {
      intercept[IllegalArgumentException] {
        val aLoan = Loan(id = "123", name = "", amount = 10000, repaid = true, lastRepaymentDate = someDate("2014-10-01"), totalAmountRepaid = Some(5000), endDateOfRepaymentAP = None)
        val acctPeriodEnd = new LocalDate("2013-12-31")
        aLoan.isRepaymentLaterReliefNowDue(acctPeriodEnd, LPQ07(someDate("2015-10-01")))
      }
    }

    val repaymentReliefLaterThanNotYetDueTable = Table(
      ("expectedValue", "isRepaid", "repaymentDate",        "endDateOfAccountingPeriodDuringWhichRepaymentWasMade",     "filingDate"),
      (false,           true,       someDate("2014-09-30"),     someDate("2014-12-31"),                                               someDate("2015-10-01")),  // repayment within 9 months
      (false,            true,       someDate("2014-10-01"),     someDate("2014-12-31"),                                               someDate("2015-10-01")),  // relief due now
      (true,           true,       someDate("2014-10-01"),     someDate("2014-12-31"),                                               someDate("2015-09-30")),  // filing date within 9 months - GOOD
      (false,            true,       someDate("2014-10-01"),     someDate("2014-12-31"),                                               someDate("2015-10-01")),  // filing date more that 9 months
      (false,           false,      None,                       someDate("2014-12-31"),                                               someDate("2015-10-01")),  // not repaid
      (false,           false,      someDate("2014-10-01"),     someDate("2014-12-31"),                                               someDate("2015-10-01")),  // not repaid
      (true,           true,       someDate("2014-10-01"),     someDate("2014-12-31"),                                               None),   // no filing date - meaning LPQ06 == true ie filied within 9 months
      (false,            true,      someDate("2014-09-30"),     None,                                                                 someDate("2015-10-01"))   // repayment within 9 months and no end of AP date
    )
    "correctly calculate isRepaymentLaterReliefNotYetDue using loan repayments made more than 9 months after the end of the accounting period" in new LoansToParticipatorsCalculator {
      forAll(repaymentReliefLaterThanNotYetDueTable) {
        (expectedValue: Boolean, isRepaid:Boolean, repaymentDate:Option[LocalDate], endDateOfAccountingPeriodDuringWhichRepaymentWasMade: Option[LocalDate], filingDate: Option[LocalDate]) => {
          val aLoan = Loan(id = "123", name = "", amount = 10000, repaid = isRepaid, lastRepaymentDate = repaymentDate, totalAmountRepaid = Some(5000), endDateOfRepaymentAP = endDateOfAccountingPeriodDuringWhichRepaymentWasMade)
          val acctPeriodEnd = new LocalDate("2013-12-31")
          aLoan.isRepaymentLaterReliefNotYetDue(acctPeriodEnd, LPQ07(filingDate)) shouldBe expectedValue
        }
      }
    }
    "throw exception when repayment date is missing on call to isRepaymentLaterReliefNotYetDue" in {
      intercept[IllegalArgumentException] {
        val aLoan = Loan(id = "123", name = "", amount = 10000, repaid = true, lastRepaymentDate = someDate("2014-10-01"), totalAmountRepaid = Some(5000), endDateOfRepaymentAP = None)
        val acctPeriodEnd = new LocalDate("2013-12-31")
        aLoan.isRepaymentLaterReliefNotYetDue(acctPeriodEnd, LPQ07(someDate("2015-10-01")))
      }
    }


    val writeOffReliefLaterThanNotYetDueTable = Table(
      ("expectedValue", "isRepaid",     "dateWrittenOff",  "endDateOfAccountingPeriodDuringWhichRepaymentWasMade",               "filingDate"),
      (false,           true,           "2014-09-30",     someDate("2014-12-31"),                                               someDate("2015-10-01")),  // repayment within 9 months
      (false,           true,           "2014-10-01",     someDate("2014-12-31"),                                               someDate("2015-10-01")),  // relief due now
      (true,            true,           "2014-10-01",     someDate("2014-12-31"),                                               someDate("2015-09-30")),  // filing date within 9 months - GOOD
      (false,           true,           "2014-10-01",     someDate("2014-12-31"),                                               someDate("2015-10-01")),  // filing date more that 9 months
      (false,           false,          "2014-10-01",     someDate("2014-12-31"),                                               someDate("2015-10-01"))   // not repaid
    )
    "correctly calculate isWriteOffLaterReliefNotYetDue using loan writeOffs made more than 9 months after the end of the accounting period" in new LoansToParticipatorsCalculator {
      forAll(writeOffReliefLaterThanNotYetDueTable) {
        (expectedValue: Boolean, isRepaid:Boolean, dateWrittenOff:String, endDateOfWriteOffAP: Option[LocalDate], filingDate: Option[LocalDate]) => {
          val writeOff = WriteOff(loanId = "123", amountWrittenOff = 10, dateWrittenOff = new LocalDate(dateWrittenOff), endDateOfWriteOffAP = endDateOfWriteOffAP)
          val acctPeriodEnd = new LocalDate("2013-12-31")
          writeOff.isLaterReliefNotYetDue(acctPeriodEnd, LPQ07(filingDate)) shouldBe expectedValue
        }
      }
    }

    "throw exception on call to iswriteOffLaterReliefNotYetDue when endDateOfWriteOffAP date is missing, and dateWrittenOff > 9 months after end of AP" in {
      intercept[IllegalArgumentException] {
        val writeOff = WriteOff(loanId = "123", amountWrittenOff = 10, dateWrittenOff = new LocalDate("2014-10-01"), endDateOfWriteOffAP = None)
        val acctPeriodEnd = new LocalDate("2013-12-31")
        writeOff.isLaterReliefNotYetDue(acctPeriodEnd, LPQ07(someDate("2015-10-01")))
      }
    }


    "return false when writeOff date is within 9 months of the end date of AP and endDateOfWriteOffAP is None" in {
      val writeOff = WriteOff(loanId = "123", amountWrittenOff = 10, dateWrittenOff = new LocalDate("2014-09-30"), endDateOfWriteOffAP = None)
      val acctPeriodEnd = new LocalDate("2013-12-31")
      writeOff.isLaterReliefNowDue(acctPeriodEnd, LPQ07(someDate("2015-10-01"))) shouldBe false
    }


    val a9Table = Table(
      ("expectedValue", "lp03", "filingDate"),
      (None, LP03(Some(List(WriteOff("123", 1, new LocalDate("1940-05-31"), Some(new LocalDate("1940-12-31")))))), someDate("1942-06-07")),
      (None, LP03(Some(List(WriteOff("123", 1, new LocalDate("1940-05-31"), None)))), someDate("1942-06-07")),
      (Some(1), LP03(Some(List(WriteOff("123", 1, new LocalDate("1940-06-01"), Some(new LocalDate("1940-12-31")))))), someDate("1941-10-01")),
      (None, LP03(Some(List(WriteOff("123", 1, new LocalDate("1940-06-01"), Some(new LocalDate("1940-12-31")))))), someDate("1941-09-30")),
      (Some(6), LP03(Some(
        List( WriteOff("123", 1, new LocalDate("1940-06-01"), someDate("1940-12-31")),
              WriteOff("456", 2, new LocalDate("1940-05-31"), someDate("1940-12-31")),
              WriteOff("789", 5, new LocalDate("1941-12-31"), someDate("1940-12-31"))))), someDate("1941-10-01"))
    )
    "correctly calculate A9 using write offs made more than 9 months after the end of the accounting period" in new LoansToParticipatorsCalculator {
      forAll(a9Table) {
        (expectedValue: Option[Int], lp03: LP03, filingDate: Option[LocalDate]) => {
          val cp2 = CP2(new LocalDate("1939-08-31"))
          calculateA9(cp2, lp03, LPQ07(filingDate)) shouldBe A9(expectedValue)
        }
      }
    }


    val a9InverseTable = Table(
      ("A9InverseExpectedValue", "lp03", "filingDate"),
      (0, LP03(Some(List(WriteOff("123", 1, new LocalDate("2014-05-31"), someDate("2014-12-31"))))), someDate("2015-06-01")),
      (0, LP03(Some(List(WriteOff("123", 1, new LocalDate("2014-06-01"), None)))), someDate("2015-10-01")),
      (0, LP03(Some(List(WriteOff("123", 1, new LocalDate("2014-06-01"), someDate("2014-12-31"))))), someDate("2014-10-01")),
      (1, LP03(Some(List(WriteOff("123", 1, new LocalDate("2014-10-01"), someDate("2014-12-31"))))), someDate("2015-09-30")),
      (2, LP03(Some(
        List( WriteOff("123", 1, new LocalDate("2014-05-31"), someDate("2014-12-31")),
          WriteOff("456", 2, new LocalDate("2014-10-01"), someDate("2014-12-31")),
          WriteOff("789", 5, new LocalDate("2014-06-01"), someDate("2014-12-31"))))), someDate("2015-09-30"))
    )
    "correctly calculate A9Inverse using write offs made more than 9 months after the end of the accounting period" in new LoansToParticipatorsCalculator {
      forAll(a9InverseTable) {
        (expectedValue: Int, lp03: LP03, filingDate: Option[LocalDate]) => {
          val cp2 = CP2(new LocalDate("2013-12-31"))
          calculateA9Inverse(cp2, lp03, LPQ07(filingDate)) shouldBe A9Inverse(Some(expectedValue))
        }
      }
    }


    val writeOffRelief = Table(
      ("expectedValue",   "dateWrittenOff",   "endDateOfWriteOffAP",  "filingDate"),
      (false,             "1940-09-30",       "1940-12-31",           "1940-11-1"),
      (true,              "1940-10-01",       "1940-12-31",           "1941-10-1"),
      (false,              "1940-10-01",      "1940-12-31",           "1940-09-30")
    )

    "correctly calculate if relief is due on write offs after 9 months" in new LoansToParticipatorsCalculator {
      forAll(writeOffRelief) {
        (expectedValue: Boolean, dateWrittenOff: String, endDateOfWriteOffAP: String, filingDate: String) => {
          val cp2 = CP2(new LocalDate("1939-12-31"))
          val writeOff = WriteOff(loanId = "123", amountWrittenOff = 10, dateWrittenOff = new LocalDate(dateWrittenOff), endDateOfWriteOffAP = someDate(endDateOfWriteOffAP))
          writeOff.isLaterReliefNowDue(cp2.value, LPQ07(someDate(filingDate))) shouldBe expectedValue
        }
      }
    }

    "throw exception when writeOffDate is more than 9 months after AP end, but endDateOfWriteOffAP is None" in {
      intercept[IllegalArgumentException] {
        val writeOff = WriteOff(loanId = "123", amountWrittenOff = 10, dateWrittenOff = new LocalDate("1940-10-01"), endDateOfWriteOffAP = None)
        val cp2 = CP2(new LocalDate("1939-12-31"))
        writeOff.isLaterReliefNowDue(cp2.value, LPQ07(someDate("1941-10-1")))
      }
    }

    "not throw exception when writeOffDate is more than 9 months after AP end, but filing date is not set (implying that filing is within 9 months of end of AP)" in new LoansToParticipatorsCalculator {
        val writeOff = WriteOff(loanId = "123", amountWrittenOff = 10, dateWrittenOff = new LocalDate("2014-10-02"), endDateOfWriteOffAP = Some(new LocalDate("2014-12-31")))
        val cp2 = CP2(new LocalDate("2013-12-31")) // AP end date
        writeOff.isReliefEarlierThanDue(cp2.value) shouldBe false
        writeOff.isLaterReliefNowDue(cp2.value, LPQ07(None)) shouldBe false
        calculateA9(cp2, LP03(Some(List(writeOff))), LPQ07(None)) shouldBe A9(None)
        calculateA9Inverse(cp2, LP03(Some(List(writeOff))), LPQ07(None)) shouldBe A9Inverse(Some(10))
    }

    "correctly calculate A10 as A9+A10" in new LoansToParticipatorsCalculator {
      calculateA10(A8(Some(4)), A9(Some(5))) shouldBe A10(Some(9))
      calculateA10(A8(None), A9(Some(5))) shouldBe A10(Some(5))
      calculateA10(A8(Some(4)), A9(None)) shouldBe A10(Some(4))
    }

    "correctly calculate A10Inverse as A9Inverse+A10Inverse" in new LoansToParticipatorsCalculator {
      calculateA10Inverse(A8Inverse(Some(4)), A9Inverse(Some(5))) shouldBe A10Inverse(Some(9))
      calculateA10Inverse(A8Inverse(None), A9Inverse(Some(5))) shouldBe A10Inverse(Some(5))
      calculateA10Inverse(A8Inverse(Some(4)), A9Inverse(None)) shouldBe A10Inverse(Some(4))
    }
    
    "correctly calculate A11 as 25% of A10" in new LoansToParticipatorsCalculator {
      calculateA11(A10(Some(1))) shouldBe A11(Some(0.25))
      calculateA11(A10(Some(333))) shouldBe A11(Some(83.25))
    }

    "correctly calculate A11Inverse as 25% of A10Inverse" in new LoansToParticipatorsCalculator {
      calculateA11Inverse(A10Inverse(Some(1))) shouldBe A11Inverse(Some(0.25))
      calculateA11Inverse(A10Inverse(Some(333))) shouldBe A11Inverse(Some(83.25))
    }
    
    "correctly calculate A12, total outstanding loans, as A2 + LP04" in new LoansToParticipatorsCalculator {
      calculateA12(A2(None), LP04(None)) shouldBe A12(Some(0))
      calculateA12(A2(None), LP04(Some(4))) shouldBe A12(Some(4))
      calculateA12(A2(Some(4)), LP04(None)) shouldBe A12(Some(4))
      calculateA12(A2(Some(3)), LP04(Some(5))) shouldBe A12(Some(8))
    }

    "correctly calculate A13 as A3 minus (the sum of Boxes A7 and A11)" in new LoansToParticipatorsCalculator {
      calculateA13(a3 = A3(Some(100)), a7 = A7(Some(7.99)), a11 = A11(Some(11))) shouldBe A13(Some(81.01))
      calculateA13(a3 = A3(Some(100.30)), a7 = A7(Some(7.99)), a11 = A11(Some(11))) shouldBe A13(Some(81.31))
      calculateA13(a3 = A3(Some(100)), a7 = A7(Some(7)), a11 = A11(Some(11))) shouldBe A13(Some(82))
      calculateA13(a3 = A3(Some(45.75)), a7 = A7(Some(7.25)), a11 = A11(Some(11))) shouldBe A13(Some(27.5))
      calculateA13(a3 = A3(Some(7.25)), a7 = A7(Some(7)), a11 = A11(Some(11))) shouldBe A13(Some(-10.75))
      calculateA13(a3 = A3(None), a7 = A7(None), a11 = A11(None)) shouldBe A13(None)
      calculateA13(a3 = A3(Some(100)), a7 = A7(None), a11 = A11(None)) shouldBe A13(Some(100))
    }

    "correctly calculate B80 as true when A11 > 0, otherwise none" in new LoansToParticipatorsCalculator {
      calculateB80(A11(None)) shouldBe B80(None)
      calculateB80(A11(Some(0))) shouldBe B80(None)
      calculateB80(A11(Some(-1))) shouldBe B80(None)
      calculateB80(A11(Some(1))) shouldBe B80(Some(true))
    }

  }


}
