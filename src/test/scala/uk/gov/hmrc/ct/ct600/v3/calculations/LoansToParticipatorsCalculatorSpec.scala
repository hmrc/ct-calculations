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

package uk.gov.hmrc.ct.ct600.v3.calculations

import org.joda.time.LocalDate
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.CP2
import uk.gov.hmrc.ct.ct600.v3.B485
import uk.gov.hmrc.ct.ct600a.v3._

class LoansToParticipatorsCalculatorSpec extends WordSpec with Matchers {

  def someDate(value:String):Option[LocalDate] = Some(new LocalDate(value))

  val lpq01Table = Table(
    ("lpq04",     "lpq10",      "A5",         "lpq03",      "expectedLpq01"),
    (Some(true),  Some(true),   Some(true),   Some(true),   true),
    (Some(false), Some(true),   Some(true),   Some(true),   false),
    (Some(false), Some(false),  Some(true),   Some(true),   false),
    (Some(false), Some(false),  Some(false),  Some(true),   false),
    (Some(false), Some(false),  Some(false),  Some(false),  false),
    (Some(true),  Some(false),  Some(true),   Some(true),   true),
    (Some(true),  Some(false),  Some(false),  Some(true),   true),
    (Some(true),  Some(false),  Some(false),  Some(false),  false),
    (None,        Some(true),   Some(true),   Some(true),   false),
    (None,        Some(false),  Some(false),  Some(false),  false),
    (Some(true),  None,         Some(true),   Some(true),   true),
    (Some(true),  None,         None,         Some(true),   true),
    (Some(true),  None,         None,         None,         false),
    (Some(true),  Some(true),   None,         None,         true),
    (Some(false), None,         Some(true),   Some(true),   false),
    (Some(false), None,         None,         Some(true),   false),
    (Some(false), None,         None,         None,         false),
    (Some(false), Some(true),   None,         None,         false),
    (Some(false), None,         Some(true),   Some(true),   false),
    (Some(false), None,         None,         Some(true),   false),
    (Some(false), None,         None,         None,         false),
    (None,        None,         None,         None,         false)
  )
  "LoansToParticipatorsCalculator" should {
    "correctly validate LPQ01 " in new LoansToParticipatorsCalculator {
      forAll(lpq01Table) {
        (lpq04: Option[Boolean], lpq10: Option[Boolean], a5: Option[Boolean], lpq03: Option[Boolean], expected: Boolean) => {
          calculateLPQ01(LPQ04(lpq04), LPQ10(lpq10), A5(a5), LPQ03(lpq03)) shouldBe LPQ01(expected)
        }
      }
    }

    val loans2PTable = Table(
      ("expectedValue", "loans2p"),
      (0, LoansToParticipators(Nil)),
      (1, LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(1)) :: Nil)),
      (6, LoansToParticipators(loans =
          Loan(id = "1", name = Some("Bilbo"), amount = Some(1), repaymentWithin9Months = Some(Repayment(id = "1", amount = Some(99), date = Some(new LocalDate("1939-09-02"))))) ::
          Loan(id = "2", name = Some("Frodo"), amount = Some(2), repaymentWithin9Months = Some(Repayment(id = "1", amount = Some(99), date = Some(new LocalDate("1939-08-30"))))) ::
          Loan(id = "3", name = Some("Gandalf"), amount = Some(3)) :: Nil))
    )
    "correctly calculate A15 (A2v2)" in new LoansToParticipatorsCalculator {
      forAll(loans2PTable) {
        (expectedValue: Int, loans2p: LoansToParticipators) => {
          calculateA15(loans2p) shouldBe A15(Some(expectedValue))
        }
      }
    }

    "correctly calculate A20 (A3v2) applying old tax rate for accounting periods ending before 6 April 2016" in new LoansToParticipatorsCalculator {

      val unusedLoans = LoansToParticipators()
      val before6April2016 = CP2(new LocalDate("2014-12-31"))

      calculateA20(A15(Some(1)), unusedLoans, before6April2016) shouldBe A20(Some(0.25))
      calculateA20(A15(Some(333)), unusedLoans, before6April2016) shouldBe A20(Some(83.25))
    }

    "correctly calculate A20 (A3v2) applying new tax rate for accounting periods ending on or after 6 April 2016" in new LoansToParticipatorsCalculator {

      val loans2p_None = LoansToParticipators(loan(amountBefore06042016 = None))
      val loans2p_0 = LoansToParticipators(loan(amountBefore06042016 = Some(0)))
      val loans2p_1 = LoansToParticipators(loan(amountBefore06042016 = Some(1)))
      val loans2p_3 = LoansToParticipators(loan(amountBefore06042016 = Some(3)))
      val loasn2p_4 = LoansToParticipators(loans = loans2p_None.loans ::: loans2p_0.loans ::: loans2p_1.loans ::: loans2p_3.loans ::: Nil)

      val after6April2016 = CP2(new LocalDate("2016-12-31"))

      calculateA20(A15(Some(1)), loans2p_None, after6April2016) shouldBe A20(Some(0.325))
      calculateA20(A15(Some(1)), loans2p_0, after6April2016) shouldBe A20(Some(0.325))
      calculateA20(A15(Some(1)), loans2p_1, after6April2016) shouldBe A20(Some(0.25))
      calculateA20(A15(Some(10)), loans2p_3, after6April2016) shouldBe A20(Some(3.025))
      calculateA20(A15(Some(333)),loasn2p_4, after6April2016 ) shouldBe A20(Some(107.925))

      val on6April2016 = CP2(new LocalDate("2016-04-06"))

      calculateA20(A15(Some(1)), loans2p_None, on6April2016) shouldBe A20(Some(0.325))
      calculateA20(A15(Some(1)), loans2p_0, on6April2016) shouldBe A20(Some(0.325))
      calculateA20(A15(Some(1)), loans2p_1, on6April2016) shouldBe A20(Some(0.25))
      calculateA20(A15(Some(10)), loans2p_3, on6April2016) shouldBe A20(Some(3.025))
      calculateA20(A15(Some(333)),loasn2p_4, on6April2016 ) shouldBe A20(Some(107.925))
    }


    val reliefDueNowOnLoanTable = Table(
      ("expectedValue", "repaymentDate"),
      (true,            Some(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2014-09-30"))))),
      (false,           Some(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2014-10-01")), endDateOfAP = someDate("2014-12-31")))), // illegal state - boolean says yes but repayment is outside 9 months
      (false,           Some(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2013-12-31"))))) // illegal state - boolean says yes but repayment is outside 9 months
    )
    "correctly calculate whether relief is due now for loans repaid within 9 months of end of AP" in new LoansToParticipatorsCalculator {
      forAll(reliefDueNowOnLoanTable) {
        (expectedValue: Boolean, repayment: Option[Repayment]) => {
          val aLoan = Loan(id = "1", name = Some("Bilbo"), amount = Some(10000), repaymentWithin9Months = repayment)
          val acctPeriodEnd = new LocalDate("2013-12-31")
          aLoan.repaymentWithin9Months.get.isReliefEarlierThanDue(acctPeriodEnd) shouldBe expectedValue
        }
      }
    }

    val reliefDueNowOnWriteOffTable = Table(
      ("expectedValue", "dateWrittenOff"),
      (false,           "2013-12-31"), // during AP - too early
      (true,            "2014-01-01"), // ok - within 9 months
      (true,            "2014-09-30"), // ok - within 9 months
      (false,           "2014-10-01") // too late
    )
    "correctly calculate whether relief is due now for write offs made within 9 months of end of AP" in new LoansToParticipatorsCalculator {
      forAll(reliefDueNowOnWriteOffTable) {
        (expectedValue: Boolean, dateWrittenOff: String) => {

          val writeOff = WriteOff(id = "123", amount = Some(10), date = Some(new LocalDate(dateWrittenOff)), endDateOfAP = someDate("2050-12-31"))
          val acctPeriodEnd = new LocalDate("2013-12-31")
          writeOff.isReliefEarlierThanDue(acctPeriodEnd) shouldBe expectedValue
        }
      }
    }

    val a30Table = Table(
      ("expectedValue", "loans2p"),
      (None, LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(123), repaymentWithin9Months = Some(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2012-12-31"))))) :: Nil)),  // illegal state - boolean says yes but repaid before AP end
      (Some(1), LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(123), repaymentWithin9Months = Some(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2013-01-01"))))) :: Nil)),  // ok
      (Some(1), LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(123), repaymentWithin9Months = Some(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2013-09-30"))))) :: Nil)), // ok
      (None, LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(123), repaymentWithin9Months = Some(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2013-10-01")), endDateOfAP = someDate("2013-12-31")))) :: Nil)), // repaid after 9 month period
      (None, LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(123), repaymentWithin9Months = None) :: Nil)), // No repayment within 9 months
      (Some(4), LoansToParticipators(loans =
          Loan(id = "1", name = Some("Bilbo"), amount = Some(123), repaymentWithin9Months = Some(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2013-01-01"))))) ::
          Loan(id = "1", name = Some("Frodo"), amount = Some(456), repaymentWithin9Months = Some(Repayment(id = "1", amount = Some(2), date = Some(new LocalDate("2012-12-31"))))) ::
          Loan(id = "1", name = Some("Smaug"), amount = Some(99999999), repaymentWithin9Months = Some(Repayment(id = "1", amount = Some(2), date = Some(new LocalDate("2013-10-01")), endDateOfAP = someDate("2013-12-31")))) ::
          Loan(id = "1", name = Some("Smaug"), amount = Some(99999999), repaymentWithin9Months = None) ::
          Loan(id = "1", name = Some("Gandalf"), amount = Some(789), repaymentWithin9Months = Some(Repayment(id = "1", amount = Some(3), date = Some(new LocalDate("2013-09-30"))))) :: Nil))
    )
    "correctly calculate A30 (A4v2) using loan repayments made between the end of the accounting period and 9months and 1 day later" in new LoansToParticipatorsCalculator {
      forAll(a30Table) {
        (expectedValue: Option[Int], loans2p: LoansToParticipators) => {
          val cp2 = CP2(new LocalDate("2012-12-31"))
          calculateA30(cp2, loans2p) shouldBe A30(expectedValue)
        }
      }
    }

    val a35Table = Table(
      ("expectedValue", "loans2p"),
      (None, LoansToParticipators(loans =  Loan(id = "1", name = Some("Bilbo"),amount = Some(100), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2012-12-31"))))) :: Nil)), // too early
      (Some(1), LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(200), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2013-01-01"))))) :: Nil)),  // ok
      (Some(1), LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(250), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2013-09-30"))))) :: Nil)), // ok
      (None, LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(375), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2013-10-01")), someDate("2013-12-31")))) :: Nil)),  // too late
      (Some(4), LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(50), writeOffs = List(
          WriteOff("123", Some(1), None, Some(new LocalDate("2013-01-01"))),
          WriteOff("456", Some(2), None, Some(new LocalDate("2013-10-01")), someDate("2013-12-31")),
          WriteOff("789", Some(3), None, Some(new LocalDate("2013-09-30"))))) :: Nil)
        )
      )
    "correctly validate A35 (A5v2) using write offs made between the end of the accounting period and 9months and 1 day later" in new LoansToParticipatorsCalculator {
      forAll(a35Table) {
        (expectedValue: Option[Int], loans2p: LoansToParticipators) => {
          val cp2 = CP2(new LocalDate("2012-12-31"))
          calculateA35(cp2, loans2p) shouldBe A35(expectedValue)
        }
      }
    }

    "correctly calculate A40 (A6v2)" in new LoansToParticipatorsCalculator {
      calculateA40(A30(Some(4)), A35(Some(5))) shouldBe A40(Some(9))
      calculateA40(A30(None), A35(Some(5))) shouldBe A40(Some(5))
      calculateA40(A30(Some(4)), A35(None)) shouldBe A40(Some(4))
    }

    "correctly calculate A45 (A7v2) applying old tax rate for accounting periods ending before 6 April 2016" in new LoansToParticipatorsCalculator {

      val unusedLoans = LoansToParticipators()
      val before6April2016 = CP2(new LocalDate("2014-12-31"))

      calculateA45(A40(Some(1)), unusedLoans, before6April2016) shouldBe A45(Some(0.25))
      calculateA45(A40(Some(333)), unusedLoans, before6April2016) shouldBe A45(Some(83.25))
    }

    "correctly calculate A45 (A7v2) applying new tax rate for accounting periods ending on or after 6 April 2016" in new LoansToParticipatorsCalculator {

      val l2p_None = LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(123), repaymentWithin9Months = None) :: Nil) // illegal state - boolean says yes but repaid before AP end
      val l2p_Invalid = LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(123), repaymentWithin9Months = Some(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2012-12-31")))), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2012-12-31"))))) ::
                                                    Loan(id = "1", name = Some("Bilbo"), amount = Some(375), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2013-10-01")), someDate("2013-12-31")))) :: Nil) // not valid repayments and writeoffs
      val l2p_1 = LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(123), repaymentWithin9Months = Some(Repayment(id = "1", amount = Some(4), amountBefore06042016 = Some(3), date = Some(new LocalDate("2017-01-01")))), writeOffs = List(WriteOff("123", Some(7), amountBefore06042016 = Some(2), Some(new LocalDate("2017-01-01"))))) :: Nil)  // ok
      val l2p_2 = LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(123), repaymentWithin9Months = Some(Repayment(id = "1", amount = Some(4), amountBefore06042016 = Some(300), date = Some(new LocalDate("2017-01-01")))), writeOffs = List(WriteOff("123", Some(7), amountBefore06042016 = Some(33), Some(new LocalDate("2017-01-01"))))) :: Nil)  // ok

      val after6April2016 = CP2(new LocalDate("2016-12-31"))
      calculateA45(A40(Some(1)), l2p_None, after6April2016) shouldBe A45(Some(0.325))
      calculateA45(A40(Some(1)), l2p_Invalid, after6April2016) shouldBe A45(Some(0.325))
      calculateA45(A40(Some(333)), l2p_None, after6April2016) shouldBe A45(Some(108.225))
      calculateA45(A40(Some(333)), l2p_1, after6April2016) shouldBe A45(Some(107.850))
      calculateA45(A40(Some(333)), l2p_2, after6April2016) shouldBe A45(Some(83.25))

      val on6April2016 = CP2(new LocalDate("2016-04-06"))
      calculateA45(A40(Some(1)), l2p_None, on6April2016) shouldBe A45(Some(0.325))
      calculateA45(A40(Some(1)), l2p_Invalid, on6April2016) shouldBe A45(Some(0.325))
      calculateA45(A40(Some(333)), l2p_None, on6April2016) shouldBe A45(Some(108.225))
      calculateA45(A40(Some(333)), l2p_1, on6April2016) shouldBe A45(Some(107.850))
      calculateA45(A40(Some(333)), l2p_2, on6April2016) shouldBe A45(Some(83.25))

    }

//    total of repayments made after (APend + 9months)
    val a55Table = Table(
      ("expectedValue", "loans2p", "filingDate"),
      (None, LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123), otherRepayments =
            List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2013-08-31")), endDateOfAP = someDate("2013-12-31")))))), LPQ07(someDate("2014-10-01"))),  //illegal state - not >9months after AP end
      (Some(1), LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123),
            otherRepayments = List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2013-10-01")), endDateOfAP = someDate("2013-12-31")))))), LPQ07(someDate("2014-10-01"))),  // ok
      (Some(1), LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123),
            otherRepayments = List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2013-12-31")), endDateOfAP = someDate("2013-12-31")))))), LPQ07(someDate("2014-10-01"))),  // ok
      (None, LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123),
          otherRepayments = List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2014-01-01")), endDateOfAP = someDate("2014-12-31")))))), LPQ07(someDate("2014-01-01"))),  // too late for this filing date
      (Some(6), LoansToParticipators(loans =
          Loan(id = "1", name = Some("Bilbo"), amount = Some(123), otherRepayments = List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2013-10-01")), endDateOfAP = someDate("2013-12-31"))) ) ::
          Loan(id = "2", name = Some("Frodo"), amount = Some(456), otherRepayments = List(Repayment(id = "1", amount = Some(3), date = Some(new LocalDate("2013-09-30")), endDateOfAP = someDate("2013-12-31"))) ) ::
          Loan(id = "3", name = Some("Gandalf"), amount = Some(789), otherRepayments = List(Repayment(id = "1", amount = Some(5), date = Some(new LocalDate("2014-01-31")), endDateOfAP = someDate("2014-12-31"))) ) :: Nil), LPQ07(someDate("2015-10-01")))
    )
    "correctly calculate A55 (A8v2) using loan repayments made more than 9 months after the end of the accounting period " in new LoansToParticipatorsCalculator {
      forAll(a55Table) {
        (expectedValue: Option[Int], loans2p: LoansToParticipators, filingDate: LPQ07) => {
          val apEndDate = CP2(new LocalDate("2012-12-31"))
          calculateA55(apEndDate, loans2p, filingDate) shouldBe A55(expectedValue)
        }
      }
    }

    val A55InverseTable = Table(
      ("expectedValue", "loans2p", "filingDate"),
      (None, LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123), otherRepayments =
          List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2014-05-31")), endDateOfAP = someDate("2014-12-31")))))), LPQ07(someDate("2015-06-01"))), //repayment too early
      (None, LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123), otherRepayments =
          List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2014-06-01")), endDateOfAP = someDate("2014-12-31")))))), LPQ07(someDate("2015-10-01"))), // relief due now
      (Some(1), LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123), otherRepayments =
          List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2014-10-01")), endDateOfAP = someDate("2014-12-31")))))), LPQ07(someDate("2015-09-29"))), // filing date early - relief not yet due
      (Some(1), LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123), otherRepayments =
          List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2014-11-01")), endDateOfAP = someDate("2014-12-31")))))), LPQ07(None)), // no filing date - meaning LPQ06 == true ie filied within 9 months
      (Some(2), LoansToParticipators(loans = List(
          Loan(id = "1", name = Some("Bilbo"), amount = Some(123), otherRepayments = List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2014-05-31")), endDateOfAP = someDate("2014-12-31")))),
          Loan(id = "1", name = Some("Frodo"), amount = Some(456), otherRepayments = List(Repayment(id = "1", amount = Some(2), date = Some(new LocalDate("2014-10-01")), endDateOfAP = someDate("2014-12-31")))),
          Loan(id = "1", name = Some("Gandalf"), amount = Some(789), otherRepayments = List(Repayment(id = "1", amount = Some(5), date = Some(new LocalDate("2014-06-01")), endDateOfAP = someDate("2014-12-31"))))
      )), LPQ07(someDate("2015-09-30")))
    )
    "correctly calculate A55Inverse using loan repayments made more than 9 months after the end of the accounting period " in new LoansToParticipatorsCalculator {
      forAll(A55InverseTable) {
        (expectedValue: Option[Int], loans2p: LoansToParticipators, filingDate: LPQ07) => {
          val cp2 = CP2(new LocalDate("2013-12-31"))
          calculateA55Inverse(cp2, loans2p, filingDate) shouldBe A55Inverse(expectedValue)
        }
      }
    }


    val reliefLaterThanDueNowTable = Table(
      ("expectedValue", "isRepaid", "repaymentDate",  "endDateOfAccountingPeriodDuringWhichRepaymentWasMade",     "filingDate"),
      (false,           true,       "2014-09-30",     someDate("2014-12-31"),                                     someDate("2015-10-01")),  // repayment too early
      (true,            true,       "2014-10-01",     someDate("2014-12-31"),                                     someDate("2015-10-01")),  // ok
      (false,           true,       "2014-10-01",     someDate("2014-12-31"),                                     someDate("2015-09-30")),  // filing date too early
      (true,            true,       "2014-10-01",     someDate("2014-12-31"),                                     someDate("2015-10-01")), // ok
      (false,           true,       "2014-09-30",     None,                                                       someDate("2015-10-01"))  // illegal state - payment inside 9 months
    )
    "correctly calculate isRepaymentLaterReliefNowDue using loan repayments made more than 9 months after the end of the accounting period" in new LoansToParticipatorsCalculator {
      forAll(reliefLaterThanDueNowTable) {
        (expectedValue: Boolean, isRepaid:Boolean, repaymentDate:String, endDateOfAccountingPeriodDuringWhichRepaymentWasMade: Option[LocalDate], filingDate: Option[LocalDate]) => {
          val aLoan = Loan(id = "1", name = Some("Bilbo"), amount = Some(10000), otherRepayments =
            List(Repayment(id = "1", amount = Some(5000), date = Some(new LocalDate(repaymentDate)), endDateOfAP = endDateOfAccountingPeriodDuringWhichRepaymentWasMade)))
          val acctPeriodEnd = new LocalDate("2013-12-31")
          aLoan.otherRepayments.head.isLaterReliefNowDue(acctPeriodEnd, LPQ07(filingDate)) shouldBe expectedValue
        }
      }
    }

    val repaymentReliefLaterThanNotYetDueTable = Table(
      ("expectedValue", "repaymentDate",  "endDateOfAccountingPeriodDuringWhichRepaymentWasMade",     "filingDate"),
      (false,           "2014-09-30",     someDate("2014-12-31"),                                      someDate("2015-10-01")),  // repayment within 9 months
      (false,           "2014-10-01",     someDate("2014-12-31"),                                      someDate("2015-10-01")),  // relief due now
      (true,            "2014-10-01",     someDate("2014-12-31"),                                      someDate("2015-09-30")),  // filing date within 9 months - GOOD
      (false,           "2014-10-01",     someDate("2014-12-31"),                                      someDate("2015-10-01")),  // filing date more that 9 months
      (false,           "2014-10-01",     someDate("2014-12-31"),                                      someDate("2015-10-01")),  // not repaid
      (true,            "2014-10-01",     someDate("2014-12-31"),                                      None),   // no filing date - meaning LPQ06 == true ie filied within 9 months
      (false,           "2014-09-30",     None,                                                        someDate("2015-10-01"))   // repayment within 9 months and no end of AP date
    )
    "correctly calculate isRepaymentLaterReliefNotYetDue using loan repayments made more than 9 months after the end of the accounting period" in new LoansToParticipatorsCalculator {
      forAll(repaymentReliefLaterThanNotYetDueTable) {
        (expectedValue: Boolean, repaymentDate:String, endDateOfAccountingPeriodDuringWhichRepaymentWasMade: Option[LocalDate], filingDate: Option[LocalDate]) => {
          val aLoan = Loan(id = "1", name = Some("Bilbo"), amount = Some(10000), otherRepayments =
            List(Repayment(id = "1", amount = Some(5000), date = Some(new LocalDate(repaymentDate)), endDateOfAP = endDateOfAccountingPeriodDuringWhichRepaymentWasMade)))
          val acctPeriodEnd = new LocalDate("2013-12-31")
          aLoan.otherRepayments.head.isLaterReliefNotYetDue(acctPeriodEnd, LPQ07(filingDate)) shouldBe expectedValue
        }
      }
    }


    val writeOffReliefLaterThanNotYetDueTable = Table(
      ("expectedValue", "dateWrittenOff",  "endDateOfAccountingPeriodDuringWhichRepaymentWasMade",   "filingDate"),
      (false,           "2014-09-30",       someDate("2014-12-31"),                                  someDate("2015-10-01")),  // repayment within 9 months
      (false,           "2014-10-01",       someDate("2014-12-31"),                                  someDate("2015-10-01")),  // relief due now
      (true,            "2014-10-01",       someDate("2014-12-31"),                                  someDate("2015-09-30")),  // filing date within 9 months - GOOD
      (false,           "2014-10-01",       someDate("2014-12-31"),                                  someDate("2015-10-01")),  // filing date more that 9 months
      (false,           "2014-10-01",       someDate("2014-12-31"),                                  someDate("2015-10-01"))   // not repaid
    )
    "correctly calculate isWriteOffLaterReliefNotYetDue using loan writeOffs made more than 9 months after the end of the accounting period" in new LoansToParticipatorsCalculator {
      forAll(writeOffReliefLaterThanNotYetDueTable) {
        (expectedValue: Boolean, dateWrittenOff:String, endDateOfWriteOffAP: Option[LocalDate], filingDate: Option[LocalDate]) => {
          val writeOff = WriteOff(id = "123", amount = Some(10), date = Some(new LocalDate(dateWrittenOff)), endDateOfAP = endDateOfWriteOffAP)
          val acctPeriodEnd = new LocalDate("2013-12-31")
          writeOff.isLaterReliefNotYetDue(acctPeriodEnd, LPQ07(filingDate)) shouldBe expectedValue
        }
      }
    }


    "return false when writeOff date is within 9 months of the end date of AP and endDateOfWriteOffAP is None" in {
      val writeOff = WriteOff(id = "123", amount = Some(10), date = Some(new LocalDate("2014-09-30")), endDateOfAP = None)
      val acctPeriodEnd = new LocalDate("2013-12-31")
      writeOff.isLaterReliefNowDue(acctPeriodEnd, LPQ07(someDate("2015-10-01"))) shouldBe false
    }


    val a60Table = Table(
      ("expectedValue", "loans2p", "filingDate"),
      (None, LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(100), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2014-09-30")), someDate("2014-12-31")))))), someDate("2015-10-01")), // too early
      (None, LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(100), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2014-09-30")), None))))), someDate("2015-10-01")),  // too early
      (Some(1), LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(100), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2014-10-01")), someDate("2014-12-31")))))), someDate("2015-10-01")),  // ok
      (None, LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(100), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2014-10-01")), someDate("2014-12-31")))))), someDate("2015-09-30")), // filing date too early
      (Some(6), LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(100), writeOffs = List(
          WriteOff("123", Some(1), None, Some(new LocalDate("2014-10-01")), someDate("2014-12-31")),
          WriteOff("456", Some(2), None, Some(new LocalDate("2014-09-30")), someDate("2014-12-31")),
          WriteOff("789", Some(5), None, Some(new LocalDate("2014-12-31")), someDate("2014-12-31")))))), someDate("2015-10-01"))
    )
    "correctly calculate A60 (A9v2) using write offs made more than 9 months after the end of the accounting period" in new LoansToParticipatorsCalculator {
      forAll(a60Table) {
        (expectedValue: Option[Int], loans2p: LoansToParticipators, filingDate: Option[LocalDate]) => {
          val cp2 = CP2(new LocalDate("2013-12-31"))
          calculateA60(cp2, loans2p, LPQ07(filingDate)) shouldBe A60(expectedValue)
        }
      }
    }


    val a60InverseTable = Table(
      ("A9InverseExpectedValue", "loans2p", "filingDate"),
      (None, LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(100), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2014-05-31")), someDate("2014-12-31")))) :: Nil), someDate("2015-06-01")),
      (None, LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(100), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2014-06-01")), None))) :: Nil), someDate("2015-10-01")),
      (None, LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(100), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2014-06-01")), someDate("2014-12-31")))) :: Nil), someDate("2014-10-01")),
      (Some(1), LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(100), writeOffs = List(WriteOff("123", Some(1), None, Some(new LocalDate("2014-10-01")), someDate("2014-12-31")))) :: Nil), someDate("2015-09-30")),
      (Some(2), LoansToParticipators(loans = Loan(id = "1", name = Some("Bilbo"), amount = Some(100), writeOffs = List(
          WriteOff("123", Some(1), None, Some(new LocalDate("2014-05-31")), someDate("2014-12-31")),
          WriteOff("456", Some(2), None, Some(new LocalDate("2014-10-01")), someDate("2014-12-31")),
          WriteOff("789", Some(5), None, Some(new LocalDate("2014-06-01")), someDate("2014-12-31")))) :: Nil), someDate("2015-09-30"))
    )
    "correctly calculate A60Inverse using write offs made more than 9 months after the end of the accounting period" in new LoansToParticipatorsCalculator {
      forAll(a60InverseTable) {
        (expectedValue: Option[Int], loans2p: LoansToParticipators, filingDate: Option[LocalDate]) => {
          val cp2 = CP2(new LocalDate("2013-12-31"))
          calculateA60Inverse(cp2, loans2p, LPQ07(filingDate)) shouldBe A60Inverse(expectedValue)
        }
      }
    }


    val writeOffRelief = Table(
      ("expectedValue",   "dateWrittenOff",   "endDateOfWriteOffAP",  "filingDate"),
      (false,             "1940-09-30",       "1940-12-31",           "1940-11-1"),
      (true,              "1940-10-01",       "1940-12-31",           "1941-10-1"),
      (false,             "1940-10-01",       "1940-12-31",           "1940-09-30")
    )

    "correctly calculate if relief is due on write offs after 9 months" in new LoansToParticipatorsCalculator {
      forAll(writeOffRelief) {
        (expectedValue: Boolean, dateWrittenOff: String, endDateOfWriteOffAP: String, filingDate: String) => {
          val cp2 = CP2(new LocalDate("1939-12-31"))
          val writeOff = WriteOff(id = "123", amount = Some(10), date = Some(new LocalDate(dateWrittenOff)), endDateOfAP = someDate(endDateOfWriteOffAP))
          writeOff.isLaterReliefNowDue(cp2.value, LPQ07(someDate(filingDate))) shouldBe expectedValue
        }
      }
    }


    "correctly calculate A65 (A10v2)" in new LoansToParticipatorsCalculator {
      calculateA65(A55(Some(4)), A60(Some(5))) shouldBe A65(Some(9))
      calculateA65(A55(None), A60(Some(5))) shouldBe A65(Some(5))
      calculateA65(A55(Some(4)), A60(None)) shouldBe A65(Some(4))
    }

    "correctly calculate A65Inverse" in new LoansToParticipatorsCalculator {
      calculateA65Inverse(A55Inverse(Some(4)), A60Inverse(Some(5))) shouldBe A65Inverse(Some(9))
      calculateA65Inverse(A55Inverse(None), A60Inverse(Some(5))) shouldBe A65Inverse(Some(5))
      calculateA65Inverse(A55Inverse(Some(4)), A60Inverse(None)) shouldBe A65Inverse(Some(4))
    }

    "correctly calculate A70 (A11v2) applying old tax rate for accounting periods ending before 6 April 2016" in new LoansToParticipatorsCalculator {

      val before6April2016 = CP2(new LocalDate("2014-12-31"))

      calculateA70(A65(Some(1)), null, before6April2016, null) shouldBe A70(Some(0.25))
      calculateA70(A65(Some(333)), null, before6April2016, null) shouldBe A70(Some(83.25))
    }

    "correctly calculate A70 (A11v2) applying new tax rate for accounting periods ending on or after 6 April 2016" in new LoansToParticipatorsCalculator {

      val l2p_invalid = LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123), otherRepayments =
        List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2013-08-31")), endDateOfAP = someDate("2013-12-31"))))))  //illegal state - not >9months after AP end
      val l2p_invalid2 = LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123),
        otherRepayments = List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2014-01-01")), endDateOfAP = someDate("2014-12-31"))))))  // too late for this filing date
      val l2p_1 = LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123),
        otherRepayments = List(Repayment(id = "1", amount = Some(1), amountBefore06042016 = Some(330), date = Some(new LocalDate("2013-10-01")), endDateOfAP = someDate("2013-12-31"))),
        writeOffs = List(
          WriteOff("123", Some(1), Some(1), Some(new LocalDate("2014-10-01")), someDate("2014-12-31")),
          WriteOff("456", Some(2), Some(1), Some(new LocalDate("2014-09-30")), someDate("2014-12-31")),
          WriteOff("789", Some(5), Some(1), Some(new LocalDate("2014-12-31")), someDate("2014-12-31"))))))
      val l2p_2 = LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123),
        otherRepayments = List(Repayment(id = "1", amount = Some(1), amountBefore06042016 = None, date = Some(new LocalDate("2013-10-01")), endDateOfAP = someDate("2013-12-31"))),
        writeOffs = List(
          WriteOff("123", Some(1), Some(0), Some(new LocalDate("2014-10-01")), someDate("2014-12-31")),
          WriteOff("789", Some(5), None, Some(new LocalDate("2014-12-31")), someDate("2014-12-31"))))))
      val l2p_3 = LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123),
        otherRepayments = List(Repayment(id = "1", amount = Some(1), amountBefore06042016 = Some(10), date = Some(new LocalDate("2013-10-01")), endDateOfAP = someDate("2013-12-31"))),
        writeOffs = List(
          WriteOff("123", Some(1), Some(5), Some(new LocalDate("2014-10-01")), someDate("2014-12-31")),
          WriteOff("789", Some(5), None, Some(new LocalDate("2014-12-31")), someDate("2014-12-31"))))))


      val after6April2016 = CP2(new LocalDate("2016-12-31"))
      calculateA70(A65(Some(1)), l2p_invalid, after6April2016, LPQ07(someDate("2014-10-01"))) shouldBe A70(Some(0.325))
      calculateA70(A65(Some(333)), l2p_invalid, after6April2016, LPQ07(someDate("2014-10-01"))) shouldBe A70(Some(108.225))
      calculateA70(A65(Some(333)), l2p_invalid2, after6April2016, LPQ07(someDate("2014-01-01"))) shouldBe A70(Some(108.225))
      calculateA70(A65(Some(333)), l2p_1, after6April2016, LPQ07(someDate("2014-01-01"))) shouldBe A70(Some(108.225))
      calculateA70(A65(Some(333)), l2p_1, after6April2016, LPQ07(someDate("2015-10-01"))) shouldBe A70(Some(83.25))
      calculateA70(A65(Some(333)), l2p_2, after6April2016, LPQ07(someDate("2015-10-01"))) shouldBe A70(Some(108.225))
      calculateA70(A65(Some(333)), l2p_3, after6April2016, LPQ07(someDate("2015-10-01"))) shouldBe A70(Some(107.100))

      val on6April2016 = CP2(new LocalDate("2016-04-06"))
      calculateA70(A65(Some(1)), l2p_invalid, on6April2016, LPQ07(someDate("2014-10-01"))) shouldBe A70(Some(0.325))
      calculateA70(A65(Some(333)), l2p_invalid, on6April2016, LPQ07(someDate("2014-10-01"))) shouldBe A70(Some(108.225))
      calculateA70(A65(Some(333)), l2p_invalid2, on6April2016, LPQ07(someDate("2014-01-01"))) shouldBe A70(Some(108.225))
      calculateA70(A65(Some(333)), l2p_1, on6April2016, LPQ07(someDate("2014-01-01"))) shouldBe A70(Some(108.225))
      calculateA70(A65(Some(333)), l2p_1, on6April2016, LPQ07(someDate("2015-10-01"))) shouldBe A70(Some(83.25))
      calculateA70(A65(Some(333)), l2p_2, on6April2016, LPQ07(someDate("2015-10-01"))) shouldBe A70(Some(108.225))
      calculateA70(A65(Some(333)), l2p_3, on6April2016, LPQ07(someDate("2015-10-01"))) shouldBe A70(Some(107.100))

    }

    "correctly calculate A70Inverse applying old tax rate for accounting periods ending before 6 April 2016" in new LoansToParticipatorsCalculator {

      val before6April2016 = CP2(new LocalDate("2014-12-31"))

      calculateA70Inverse(A65Inverse(Some(1)), null, before6April2016, null) shouldBe A70Inverse(Some(0.25))
      calculateA70Inverse(A65Inverse(Some(333)), null, before6April2016, null) shouldBe A70Inverse(Some(83.25))
    }

    "correctly calculate A70Inverse applying new tax rate for accounting periods ending on or after 6 April 2016" in new LoansToParticipatorsCalculator {

      val l2p_invalid = LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123), otherRepayments =
        List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2014-05-31")), endDateOfAP = someDate("2014-12-31"))))))
      val l2p_invalid2 = LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123),
          otherRepayments = List(Repayment(id = "1", amount = Some(1), date = Some(new LocalDate("2014-06-01")), endDateOfAP = None)))))
      val l2p_1 = LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123),
          otherRepayments = List(Repayment(id = "1", amount = Some(1), amountBefore06042016 = Some(330), date = Some(new LocalDate("2014-10-01")), endDateOfAP = someDate("2014-12-31"))),
          writeOffs = List(
            WriteOff("123", Some(1), Some(1), Some(new LocalDate("2014-10-01")), someDate("2014-12-31")),
            WriteOff("456", Some(2), Some(1), Some(new LocalDate("2014-10-01")), someDate("2014-12-31")),
            WriteOff("789", Some(5), Some(1), Some(new LocalDate("2014-10-01")), someDate("2014-12-31"))))))
      val l2p_2 = LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123),
        otherRepayments = List(Repayment(id = "1", amount = Some(1), amountBefore06042016 = None, date = Some(new LocalDate("2014-10-01")), endDateOfAP = someDate("2014-12-31"))),
        writeOffs = List(
          WriteOff("123", Some(1), Some(0), Some(new LocalDate("2014-10-01")), someDate("2014-12-31")),
          WriteOff("456", Some(2), None, Some(new LocalDate("2014-10-01")), someDate("2014-12-31")),
          WriteOff("789", Some(5), None, Some(new LocalDate("2014-10-01")), someDate("2014-12-31"))))))
      val l2p_3 = LoansToParticipators(loans = List(Loan(id = "1", name = Some("Bilbo"), amount = Some(123),
        otherRepayments = List(Repayment(id = "1", amount = Some(1), amountBefore06042016 = Some(10), date = Some(new LocalDate("2014-10-01")), endDateOfAP = someDate("2014-12-31"))),
        writeOffs = List(
          WriteOff("123", Some(1), Some(0), Some(new LocalDate("2014-10-01")), someDate("2014-12-31")),
          WriteOff("456", Some(2), Some(5), Some(new LocalDate("2014-10-01")), someDate("2014-12-31")),
          WriteOff("789", Some(5), Some(0), Some(new LocalDate("2014-10-01")), someDate("2014-12-31"))))))

      val after6April2016 = CP2(new LocalDate("2016-12-31"))
      calculateA70Inverse(A65Inverse(Some(1)), l2p_invalid, after6April2016, LPQ07(someDate("2015-06-01"))) shouldBe A70Inverse(Some(0.325))
      calculateA70Inverse(A65Inverse(Some(333)), l2p_invalid, after6April2016, LPQ07(someDate("2015-10-01"))) shouldBe A70Inverse(Some(108.225))
      calculateA70Inverse(A65Inverse(Some(333)), l2p_invalid2, after6April2016, LPQ07(someDate("2014-01-01"))) shouldBe A70Inverse(Some(108.225))
      calculateA70Inverse(A65Inverse(Some(333)), l2p_1, after6April2016, LPQ07(someDate("2015-09-30"))) shouldBe A70Inverse(Some(83.25))
      calculateA70Inverse(A65Inverse(Some(333)), l2p_2, after6April2016, LPQ07(someDate("2015-09-30"))) shouldBe A70Inverse(Some(108.225))
      calculateA70Inverse(A65Inverse(Some(333)), l2p_3, after6April2016, LPQ07(someDate("2015-09-30"))) shouldBe A70Inverse(Some(107.100))

      val on6April2016 = CP2(new LocalDate("2016-04-06"))
      calculateA70Inverse(A65Inverse(Some(1)), l2p_invalid, on6April2016, LPQ07(someDate("2015-06-01"))) shouldBe A70Inverse(Some(0.325))
      calculateA70Inverse(A65Inverse(Some(333)), l2p_invalid, on6April2016, LPQ07(someDate("2015-10-01"))) shouldBe A70Inverse(Some(108.225))
      calculateA70Inverse(A65Inverse(Some(333)), l2p_invalid2, on6April2016, LPQ07(someDate("2014-01-01"))) shouldBe A70Inverse(Some(108.225))
      calculateA70Inverse(A65Inverse(Some(333)), l2p_1, on6April2016, LPQ07(someDate("2015-09-30"))) shouldBe A70Inverse(Some(83.25))
      calculateA70Inverse(A65Inverse(Some(333)), l2p_2, on6April2016, LPQ07(someDate("2015-09-30"))) shouldBe A70Inverse(Some(108.225))
      calculateA70Inverse(A65Inverse(Some(333)), l2p_3, on6April2016, LPQ07(someDate("2015-09-30"))) shouldBe A70Inverse(Some(107.100))
    }

    "correctly calculate A75 (A12v2), total outstanding loans" in new LoansToParticipatorsCalculator {
      calculateA75(A15(None), LP04(None)) shouldBe A75(Some(0))
      calculateA75(A15(None), LP04(Some(4))) shouldBe A75(Some(4))
      calculateA75(A15(Some(40)), LP04(Some(60))) shouldBe A75(Some(100))
    }

    "correctly calculate A80 (A13v2)" in new LoansToParticipatorsCalculator {
      calculateA80(a20 = A20(Some(100)), a45 = A45(Some(7.99)), a70 = A70(Some(11))) shouldBe A80(Some(81.01))
      calculateA80(a20 = A20(Some(100.30)), a45 = A45(Some(7.99)), a70 = A70(Some(11))) shouldBe A80(Some(81.31))
      calculateA80(a20 = A20(Some(100)), a45 = A45(Some(7)), a70 = A70(Some(11))) shouldBe A80(Some(82))
      calculateA80(a20 = A20(Some(45.75)), a45 = A45(Some(7.25)), a70 = A70(Some(11))) shouldBe A80(Some(27.5))
      calculateA80(a20 = A20(Some(7.25)), a45 = A45(Some(7)), a70 = A70(Some(11))) shouldBe A80(Some(-10.75))
      calculateA80(a20 = A20(None), a45 = A45(None), a70 = A70(None)) shouldBe A80(None)
      calculateA80(a20 = A20(Some(100)), a45 = A45(None), a70 = A70(None)) shouldBe A80(Some(100))
    }

    "correctly calculate B485 (B80v2)" in new LoansToParticipatorsCalculator {
      calculateB485(A70(None)) shouldBe B485(false)
      calculateB485(A70(Some(0))) shouldBe B485(false)
      calculateB485(A70(Some(-1))) shouldBe B485(false)
      calculateB485(A70(Some(1))) shouldBe B485(true)
    }

  }
  
  def loan(amountBefore06042016: Option[Int]) = Loan(id = "1", name = Some("Bilbo"), amount = Some(1), amountBefore06042016 = amountBefore06042016) :: Nil


}
