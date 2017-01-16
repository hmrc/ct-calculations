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

package uk.gov.hmrc.ct.ct600.v3

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.CP2
import uk.gov.hmrc.ct.ct600.v3.stubs.StubbedCT600BoxRetriever
import uk.gov.hmrc.ct.ct600a.v3._

class LoansToParticipatorsSpec extends WordSpec with Matchers {

  //Know this isn't a V3 date but required so our +9 months date aren't before the current date
  val currentAPEndDate = new LocalDate(2014, 6, 1)

  val boxRetriever = new StubbedCT600BoxRetriever {
    override def cp2(): CP2 = CP2(currentAPEndDate)
    override def lpq03(): LPQ03 = LPQ03(Some(true))
  }

  val validLoan = Loan(id = "1",
    name = Some("Smurfette"),
    amount = Some(200),
    amountBefore06042016 = None)

  val validRepaymentWithin9Months = Repayment(
    id = "3",
    amount = Some(50),
    amountBefore06042016 = None,
    date = Some(currentAPEndDate.plusDays(3))
  )

  val validRepaymentAfter9Months = Repayment(
    id = "4",
    amount = Some(50),
    date = Some(currentAPEndDate.plusMonths(10)),
    amountBefore06042016 = None,
    endDateOfAP = Some(currentAPEndDate.plusYears(1))
  )

  val validWriteOff = WriteOff(
    id = "2",
    date = Some(currentAPEndDate.plusDays(2)),
    amount = Some(50),
    endDateOfAP = Some(currentAPEndDate.plusDays(1))
  )

  "Loans validation" should {

    "return an error if a loan has a name shorter then 2 characters" in {

      val l2pBox = LoansToParticipators(List(validLoan.copy(name = Some("a"))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.name.length"
    }

    "return an error if a loan has a name greater then 56 characters" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(name = Some("PneumonoultramicroscopicsilicovolcanoconiosisisgoodformeX"))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.name.length"
    }

    "return no errors if a loan has a name between 2 and 56 characters" in {
      val l2pBox = LoansToParticipators(List(
        validLoan.copy(name = Some("ab")),
        validLoan.copy(name = Some("Pneumonoultramicroscopicsilicovolcanoconiosisisgoodforme"))))

      val errors = l2pBox.validate(boxRetriever)
      errors shouldBe empty
    }

    "return an error if a loan has an amount less then 1" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amount = Some(0))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.amount.value"
    }

    "return an error if a loan has an amount greater then 99999999" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amount = Some(100000000))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.amount.value"
    }

    "be happy if a loan has a valid amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amountBefore06042016 = Some(validLoan.amount.get - 1))))

      val errors = l2pBox.validate(boxRetriever)

      errors shouldBe empty
    }

    "return an error if a loan has an amountBefore06042016 greater then amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amountBefore06042016 = Some(validLoan.amount.get + 1))))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.beforeApril2016Amount.value"
    }

    "return an error if a loan has a negative amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amountBefore06042016 = Some(-1))))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 2
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.beforeApril2016Amount.value"
      errors.last.errorMessageKey shouldBe "error.compoundList.loans.0.unbalanced.beforeApril2016Amount"
    }

    "return an error if a loan has the same name as an existing Loan" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(id = "999", name = Some("Kylo Ren")), validLoan.copy(id = "987", name = Some(" KYLO Ren "))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 2
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.1.uniqueName"
      errors.last.errorMessageKey shouldBe "error.compoundList.loans.0.uniqueName"
    }

    "return no errors if a loan has a amount between 1 and 9999999 characters, and name is unique" in {
      val l2pBox = LoansToParticipators(List(
        validLoan.copy(amount = Some(1)),
        validLoan.copy(id = "987", name = Some("Kylo Ren"), amount = Some(9999999))
      ))

      val errors = l2pBox.validate(boxRetriever)
      errors shouldBe empty
    }

    "return sorted error indexes" in {
      val l2pBox = LoansToParticipators(List(
        validLoan.copy(amount = None, name = Some("Zebra"), id = "ic"),
        validLoan.copy(amount = Some(1), name = Some("Aardvark"), id = "1b"),
        validLoan.copy(amount = None, name = Some("Aardvark"), id = "1a")
      ))

      val errors = l2pBox.validate(boxRetriever)
      errors should contain (CtValidation(Some("LoansToParticipators"), "error.compoundList.loans.0.uniqueName"))
      errors should contain (CtValidation(Some("LoansToParticipators"), "error.compoundList.loans.1.uniqueName"))
      errors should contain (CtValidation(Some("LoansToParticipators"), "error.compoundList.loans.0.amount.value"))
      errors should contain (CtValidation(Some("LoansToParticipators"), "error.compoundList.loans.2.amount.value"))
    }
  }

  "repaymentWithin9Months validation" should {
    "be happy if repaymentWithin9Months value is valid" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(repaymentWithin9Months = Some(validRepaymentWithin9Months))))
      val errors = l2pBox.validate(boxRetriever)
      errors shouldBe empty
    }

    "return an error if repaymentWithin9Months date is before 9 month interval" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(date = Some(currentAPEndDate.minusDays(1)))))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.repaymentWithin9Months.date.range"
    }

    "return an error if repaymentWithin9Months date is on AP end date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(date = Some(currentAPEndDate))))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.repaymentWithin9Months.date.range"
    }

    "return an error if repaymentWithin9Months date is after 9 month interval" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(date = Some(currentAPEndDate.plusMonths(10)))))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.repaymentWithin9Months.date.range"
    }

    "return an error if a repaymentWithin9Months has an amount less then 1" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amount = Some(0))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.repaymentWithin9Months.amount.value"
    }

    "return an error if a repaymentWithin9Months has an amount greater then 9999999" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amount = Some(100000000)))
      )))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 2
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.unbalanced"
      errors.last.errorMessageKey shouldBe "error.compoundList.loans.0.repaymentWithin9Months.amount.value"
    }

    "be happy if repaymentWithin9Months has valid amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amountBefore06042016 = Some(100), repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amountBefore06042016 = Some(validRepaymentWithin9Months.amount.get - 1))))))

      val errors = l2pBox.validate(boxRetriever)

      errors shouldBe empty
    }

    "return an error if a repaymentWithin9Months has a negative amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amountBefore06042016 = Some(-1))))))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.repaymentWithin9Months.beforeApril2016Amount.value"
    }

    "return an error if a repaymentWithin9Months has an amountBefore06042016 greater then amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amountBefore06042016 = Some(100),
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amountBefore06042016 = Some(validRepaymentWithin9Months.amount.get + 1)))
      )))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.last.errorMessageKey shouldBe "error.compoundList.loans.0.repaymentWithin9Months.beforeApril2016Amount.value"
    }
  }

  "repaymentAfter9Months validation" should {

    "return an error if a repaymentAfter9Months has an amount less then 1" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(amount = Some(0))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.otherRepayments.0.amount.value"
    }

    "return an error if a repaymentAfter9Months has an amount greater then 100,000,000" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(amount = Some(100000000))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 2
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.unbalanced"
      errors.last.errorMessageKey shouldBe "error.compoundList.loans.0.otherRepayments.0.amount.value"
    }

    "be happy if a repaymentAfter9Months has a valid amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amountBefore06042016 = Some(100), otherRepayments = List(validRepaymentAfter9Months.copy(amountBefore06042016 = Some(validRepaymentAfter9Months.amount.get - 1))))))

      val errors = l2pBox.validate(boxRetriever)

      errors shouldBe empty
    }

    "return an error if a repaymentAfter9Months has an amountBefore06042016 greater then amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amountBefore06042016 = Some(100), otherRepayments = List(validRepaymentAfter9Months.copy(amountBefore06042016 = Some(validRepaymentAfter9Months.amount.get + 1))))))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.last.errorMessageKey shouldBe "error.compoundList.loans.0.otherRepayments.0.beforeApril2016Amount.value"
    }

    "return an error if a repaymentAfter9Months has a negative amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(amountBefore06042016 = Some(-1))))))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.last.errorMessageKey shouldBe "error.compoundList.loans.0.otherRepayments.0.beforeApril2016Amount.value"
    }

    "return an error if a repaymentAfter9Months has a date on the accounting period end date + 9 months - 1 day" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(date = Some(currentAPEndDate.plusMonths(9).minusDays(1)))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.otherRepayments.0.date.range"
    }

    "return an error if a repaymantAfter9Months has a date after todays date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(date = Some(LocalDate.now().plusDays(1)))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.otherRepayments.0.date.range"
    }

    "not return an error if a repaymentAfter9Months has a date on todays date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(date = Some(LocalDate.now()))))))

      val errors = l2pBox.validate(boxRetriever)
      errors shouldBe empty
    }

    "return an error if a repaymentAfter9Months has no ApEndDate" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(endDateOfAP = None)))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.otherRepayments.0.endDateOfAP.required"
    }

    "return an error if a repaymentAfter9Months has a ApEndDate on the accounting period end date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(endDateOfAP = Some(currentAPEndDate))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.otherRepayments.0.endDateOfAP.range"
    }

    "return no errors if a repaymentAfter9Months has a ApEndDate after the accounting period end date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(endDateOfAP = Some(currentAPEndDate.plusDays(1)))))))

      val errors = l2pBox.validate(boxRetriever)
      errors shouldBe empty
    }

    "no if a repaymentAfter9Months is valid" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months))))

      val errors = l2pBox.validate(boxRetriever)
      errors shouldBe empty
    }

    "return sorted error indexes" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(
        validRepaymentAfter9Months.copy(amount = None, date = Some(currentAPEndDate.plusMonths(10)), id = "3"),
        validRepaymentAfter9Months.copy(amount = None, date = Some(currentAPEndDate.plusMonths(12)), id = "1"),
        validRepaymentAfter9Months.copy(amount = Some(200), date = Some(currentAPEndDate.plusMonths(11)), id = "2")
      ))))

      val errors = l2pBox.validate(boxRetriever)
      errors should contain (CtValidation(Some("LoansToParticipators"), "error.compoundList.loans.0.otherRepayments.0.amount.value"))
      errors should contain (CtValidation(Some("LoansToParticipators"), "error.compoundList.loans.0.otherRepayments.2.amount.value"))
    }

    "return repayment index with empty ids" in {
      val loanBob = Loan("1234", Some("Bob"), Some(1000), None, None, List(Repayment("37647364", Some(100), None, None, None)), List.empty)
      val repayment1 = Repayment("45e0d65240504c869e7f4ce520a8d756", None, None, None, None)
      val repayment2 = Repayment("40b9c9b7e721403984ee5779a2126ff6", None, None, None, None)
      val loanTom = Loan("837843", Some("Tom"), Some(200), None, None, List(repayment1, repayment2), List.empty)

      val l2p =  LoansToParticipators(List(loanTom, loanBob))
      val loanIndex = LoansToParticipators.findLoanIndex(loanTom, l2p)

      LoansToParticipators.findOtherRepaymentIndex(loanIndex, repayment2, l2p) shouldBe 0
    }
  }

  "writeOff validation" should {

    "return an error if a write off has a date on the accounting period end date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(date = Some(currentAPEndDate.minusDays(1)))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.writeOffs.0.date.range"
    }

    "return an error if a write off has a date after todays date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(date = Some(LocalDate.now().plusDays(1)))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.writeOffs.0.date.range"
    }

    "not return an error if a write off has a date on todays date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(date = Some(LocalDate.now()))))))

      val errors = l2pBox.validate(boxRetriever)
      errors shouldBe empty
    }

    "return an error if a write off has an amount less then 1" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(amount = Some(0))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.writeOffs.0.amount.value"
    }

    "return an error if a write off has an amount greater then 99999999" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(amount = Some(100000000))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 2
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.unbalanced"
      errors.last.errorMessageKey shouldBe "error.compoundList.loans.0.writeOffs.0.amount.value"
    }

    "return no errors if a writeoff has a amount between 1 and 9999999 characters" in {
      val l2pBox = LoansToParticipators(List(
        validLoan.copy(writeOffs = List(validWriteOff.copy(amount = Some(1)))),
        validLoan.copy(writeOffs = List(validWriteOff.copy(amount = Some(60))))
      ))

      val errors = l2pBox.validate(boxRetriever)
      errors shouldBe empty
    }

    "be happy if a writeoff has a valid amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amountBefore06042016 = Some(100), writeOffs = List(validWriteOff.copy(amountBefore06042016 = Some(validWriteOff.amount.get - 1))))))

      val errors = l2pBox.validate(boxRetriever)

      errors shouldBe empty
    }

    "return an error if a writeoff has an amountBefore06042016 greater then amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amountBefore06042016 = Some(100), writeOffs = List(validWriteOff.copy(amountBefore06042016 = Some(validWriteOff.amount.get + 1))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.writeOffs.0.beforeApril2016Amount.value"
    }

    "return an error if a writeoff has a negative amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(amountBefore06042016 = Some(-1))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.writeOffs.0.beforeApril2016Amount.value"
    }

    "return an error if a write off has a date > 9 months after current AP End Date and no apEndDate" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(date = Some(currentAPEndDate.plusMonths(10)), endDateOfAP = None)))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.last.errorMessageKey shouldBe "error.compoundList.loans.0.writeOffs.0.endDateOfAP.required"
    }

    "return no error if a write off has a date <= 9 months after current AP End Date and no apEndDate" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(date = Some(currentAPEndDate.plusMonths(9)), endDateOfAP = None)))))

      val errors = l2pBox.validate(boxRetriever)
      errors shouldBe empty
    }

    "return no errors if a write off has a end date of AP after the current accounting period end date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(endDateOfAP = Some(LocalDate.now().plusDays(200)))))))

      val errors = l2pBox.validate(boxRetriever)
      errors shouldBe empty
    }

    "return sorted error indexes" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(
        validWriteOff.copy(amount = Some(200), date = Some(currentAPEndDate.plusMonths(11)), id = "2"),
        validWriteOff.copy(amount = None, date = Some(currentAPEndDate.plusMonths(12)), id = "1"),
        validWriteOff.copy(amount = None, date = Some(currentAPEndDate.plusMonths(10)), id = "3")
      ))))

      val errors = l2pBox.validate(boxRetriever)
      errors should contain (CtValidation(Some("LoansToParticipators"), "error.compoundList.loans.0.writeOffs.0.amount.value"))
      errors should contain (CtValidation(Some("LoansToParticipators"), "error.compoundList.loans.0.writeOffs.2.amount.value"))
    }

    "return write off index with empty ids" in {
      val loanBob = Loan("1234", Some("Bob"), Some(1000), None, None, List.empty, List(WriteOff("37647364", Some(100), None, None, None)))
      val writeOff1 = WriteOff("45e0d65240504c869e7f4ce520a8d756", None, None, None, None)
      val writeOff2 = WriteOff("40b9c9b7e721403984ee5779a2126ff6", None, None, None, None)
      val loanTom = Loan("837843", Some("Tom"), Some(200), None, None, List.empty, List(writeOff1, writeOff2))

      val l2p =  LoansToParticipators(List(loanTom, loanBob))
      val loanIndex = LoansToParticipators.findLoanIndex(loanTom, l2p)

      LoansToParticipators.findWriteOffIndex(loanIndex, writeOff2, l2p) shouldBe 0
    }
  }

  "global Loan validation" should {

    "not allow repaidWithin9Months amount to exceed Loan Amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amount = Some(200),
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amount = Some(201)))
      )))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.unbalanced"
    }

    "not allow total repaidAfter9Months amount to exceed Loan Amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amount = Some(200),
        otherRepayments = List(validRepaymentAfter9Months.copy(amount = Some(201)))
      )))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.unbalanced"
    }

    "not allow total writeOffs amount to exceed Loan Amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amount = Some(200),
        writeOffs = List(validWriteOff.copy(amount = Some(201)))
      )))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.unbalanced"
    }

    "not allow total of repayments and write offs amounts to exceed Loan Amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amount = Some(200),
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amount = Some(50))),
        otherRepayments = List(validRepaymentAfter9Months.copy(amount = Some(100))),
        writeOffs = List(validWriteOff.copy(amount = Some(51)))
      )))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.unbalanced"
    }

    "allow Loan with amount equal to total of repayments and write offs" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amount = Some(200),
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amount = Some(50))),
        otherRepayments = List(validRepaymentAfter9Months.copy(amount = Some(100))),
        writeOffs = List(validWriteOff.copy(amount = Some(50)))
      )))

      val errors = l2pBox.validate(boxRetriever)
      errors shouldBe empty
    }

    "not allow total of repayments and write offs amounts before April 2016 to exceed the one in Loan" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amountBefore06042016 = Some(20),
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amountBefore06042016 = Some(5))),
        otherRepayments = List(validRepaymentAfter9Months.copy(amountBefore06042016 = Some(10))),
        writeOffs = List(validWriteOff.copy(amountBefore06042016 = Some(6)))
      )))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.unbalanced.beforeApril2016Amount"
    }

    "allow total of repayments and write offs amounts before April 2016 to be equal to the one in Loan" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amountBefore06042016 = Some(20),
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amountBefore06042016 = Some(5))),
        otherRepayments = List(validRepaymentAfter9Months.copy(amountBefore06042016 = Some(10))),
        writeOffs = List(validWriteOff.copy(amountBefore06042016 = Some(5)))
      )))

      val errors = l2pBox.validate(boxRetriever)

      errors shouldBe empty
    }

    "allow total of repayments and write offs amounts before April 2016 to be less to the one in Loan" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amountBefore06042016 = Some(200),
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amountBefore06042016 = Some(5))),
        otherRepayments = List(validRepaymentAfter9Months.copy(amountBefore06042016 = Some(10))),
        writeOffs = List(validWriteOff.copy(amountBefore06042016 = Some(0)))
      )))

      val errors = l2pBox.validate(boxRetriever)

      errors shouldBe empty
    }

    "not allow total of repayments and write offs amounts before April 2016 to be less then a None Loan amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amountBefore06042016 = None,
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amountBefore06042016 = Some(1))),
        otherRepayments = List(validRepaymentAfter9Months.copy(amountBefore06042016 = Some(4))),
        writeOffs = List(validWriteOff.copy(amountBefore06042016 = Some(2)))
      )))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.compoundList.loans.0.unbalanced.beforeApril2016Amount"
    }

    "return an error if there is no loans and LPQ01 is true" in {
      val l2pBox = LoansToParticipators(List.empty)

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "error.loan.required"
    }

    "not return an error if there is no loans and LPQ01 is false" in {
      val l2pBox = LoansToParticipators(List.empty)

      val testRetriever = new StubbedCT600BoxRetriever {
        override def lpq03(): LPQ03 = LPQ03(Some(false))
      }

      val errors = l2pBox.validate(testRetriever)
      errors shouldBe empty
    }
  }

}
