/*
 * Copyright 2016 HM Revenue & Customs
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
import uk.gov.hmrc.ct.computations.CP2
import uk.gov.hmrc.ct.ct600.v3.stubs.StubbedCT600BoxRetriever
import uk.gov.hmrc.ct.ct600a.v3._

class LoansToParticipatorsSpec extends WordSpec with Matchers {

  //Know this isn't a V3 date but required so our +9 months date aren't before the current date
  val currentAPEndDate = new LocalDate(2014, 6, 1)

  val boxRetriever = new StubbedCT600BoxRetriever {
    override def retrieveCP2(): CP2 = CP2(currentAPEndDate)
    override def retrieveLPQ03(): LPQ03 = LPQ03(Some(true))
  }

  val validLoan = Loan(id = "1",
    name = "Smurfette",
    amount = 200,
    amountBefore06042016 = 0)

  val validRepaymentWithin9Months = Repayment(
    id = "3",
    amount = 50,
    amountBefore06042016 = 0,
    date = currentAPEndDate.plusDays(3)
  )

  val validRepaymentAfter9Months = Repayment(
    id = "4",
    amount = 50,
    date = currentAPEndDate.plusMonths(10),
    amountBefore06042016 = 0,
    endDateOfAP = Some(currentAPEndDate.plusYears(1))
  )

  val validWriteOff = WriteOff(
    id = "2",
    date = currentAPEndDate.plusDays(2),
    amount = 50,
    endDateOfAP = Some(currentAPEndDate.plusDays(1))
  )

  "Loans validation" should {

    "return an error if a loan has a name shorter then 2 characters" in {

      val l2pBox = LoansToParticipators(List(validLoan.copy(name = "a")))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.name.length"
    }

    "return an error if a loan has a name greater then 56 characters" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(name = "PneumonoultramicroscopicsilicovolcanoconiosisisgoodformeX")))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.name.length"
    }

    "return no errors if a loan has a name between 2 and 56 characters" in {
      val l2pBox = LoansToParticipators(List(
        validLoan.copy(name = "ab"),
        validLoan.copy(name = "Pneumonoultramicroscopicsilicovolcanoconiosisisgoodforme")))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "return an error if a loan has an amount less then 1" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amount = 0)))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.amount.value"
    }

    "return an error if a loan has an amount greater then 99999999" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amount = 100000000)))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.amount.value"
    }

    "be happy if a loan has a valid amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amountBefore06042016 = validLoan.amount - 1 )))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 0
    }

    "return an error if a loan has an amountBefore06042016 greater then amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amountBefore06042016 = validLoan.amount + 1 )))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.beforeApril2016Amount.value"
    }

    "return an error if a loan has a negative amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amountBefore06042016 = -1 )))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.beforeApril2016Amount.value"
    }

    "return an error if a loan has the same name as an existing Loan" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(name = "Kylo Ren"), validLoan.copy(id = "987", name = " KYLO Ren ")))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 2
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.uniqueName"
      errors.last.errorMessageKey shouldBe "loan.987.error.loan.uniqueName"
    }

    "return no errors if a loan has a amount between 1 and 9999999 characters, and name is unique" in {
      val l2pBox = LoansToParticipators(List(
        validLoan.copy(amount = 1),
        validLoan.copy(id = "987", name = "Kylo Ren", amount = 9999999)
      ))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }
  }

  "repaymentWithin9Months validation" should {
    "be happy if repaymentWithin9Months value is valid" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(repaymentWithin9Months = Some(validRepaymentWithin9Months))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "return an error if repaymentWithin9Months date is before 9 month interval" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(date = currentAPEndDate.minusDays(1))))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.repaymentWithin9Months.3.error.repaymentWithin9Months.date.range"
    }

    "return an error if repaymentWithin9Months date is on AP end date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(date = currentAPEndDate)))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.repaymentWithin9Months.3.error.repaymentWithin9Months.date.range"
    }

    "return an error if repaymentWithin9Months date is after 9 month interval" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(date = currentAPEndDate.plusMonths(10))))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.repaymentWithin9Months.3.error.repaymentWithin9Months.date.range"
    }

    "return an error if a repaymentWithin9Months has an amount less then 1" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amount = 0)))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.repaymentWithin9Months.3.error.repaymentWithin9Months.amount.value"
    }

    "return an error if a repaymentWithin9Months has an amount greater then 9999999" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amount = 100000000))
      )))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 2
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.unbalanced"
      errors.last.errorMessageKey shouldBe "loan.1.repaymentWithin9Months.3.error.repaymentWithin9Months.amount.value"
    }

    "be happy if repaymentWithin9Months has valid amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amountBefore06042016 = validRepaymentWithin9Months.amount - 1)))))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 0
    }

    "return an error if a repaymentWithin9Months has a negative amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amountBefore06042016 = -1)))))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.repaymentWithin9Months.3.error.repaymentWithin9Months.beforeApril2016Amount.value"
    }

    "return an error if a repaymentWithin9Months has an amountBefore06042016 greater then amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amountBefore06042016 = validRepaymentWithin9Months.amount + 1))
      )))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.last.errorMessageKey shouldBe "loan.1.repaymentWithin9Months.3.error.repaymentWithin9Months.beforeApril2016Amount.value"
    }
  }

  "repaymentAfter9Months validation" should {

    "return an error if a repaymentAfter9Months has an amount less then 1" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(amount = 0)))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.otherRepayment.4.error.otherRepayment.amount.value"
    }

    "return an error if a repaymentAfter9Months has an amount greater then 100,000,000" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(amount = 100000000)))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 2
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.unbalanced"
      errors.last.errorMessageKey shouldBe "loan.1.otherRepayment.4.error.otherRepayment.amount.value"
    }

    "be happy if a repaymentAfter9Months has a valid amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(amountBefore06042016 = validRepaymentAfter9Months.amount - 1)))))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 0
    }

    "return an error if a repaymentAfter9Months has an amountBefore06042016 greater then amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(amountBefore06042016 = validRepaymentAfter9Months.amount + 1)))))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.last.errorMessageKey shouldBe "loan.1.otherRepayment.4.error.otherRepayment.beforeApril2016Amount.value"
    }

    "return an error if a repaymentAfter9Months has a negative amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(amountBefore06042016 = -1)))))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.last.errorMessageKey shouldBe "loan.1.otherRepayment.4.error.otherRepayment.beforeApril2016Amount.value"
    }

    "return an error if a repaymentAfter9Months has a date on the accounting period end date + 9 months - 1 day" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(date = currentAPEndDate.plusMonths(9).minusDays(1))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.otherRepayment.4.error.otherRepayment.date.range"
    }

    "return an error if a repaymantAfter9Months has a date after todays date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(date = LocalDate.now().plusDays(1))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.otherRepayment.4.error.otherRepayment.date.range"
    }

    "not return an error if a repaymentAfter9Months has a date on todays date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(date = LocalDate.now())))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "return an error if a repaymentAfter9Months has no ApEndDate" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(endDateOfAP = None)))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.otherRepayment.4.error.otherRepayment.apEndDate.required"
    }

    "return an error if a repaymentAfter9Months has a ApEndDate on the accounting period end date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(endDateOfAP = Some(currentAPEndDate))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.otherRepayment.4.error.otherRepayment.apEndDate.range"
    }

    "return no errors if a repaymentAfter9Months has a ApEndDate after the accounting period end date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months.copy(endDateOfAP = Some(currentAPEndDate.plusDays(1)))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "no if a repaymentAfter9Months is valid" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(otherRepayments = List(validRepaymentAfter9Months))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }
  }

  "writeOff validation" should {

    "return an error if a write off has a date on the accounting period end date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(date = currentAPEndDate.minusDays(1))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.writeOff.2.error.writeOff.date.range"
    }

    "return an error if a write off has a date after todays date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(date = LocalDate.now().plusDays(1))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.writeOff.2.error.writeOff.date.range"
    }

    "not return an error if a write off has a date on todays date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(date = LocalDate.now())))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "return an error if a write off has an amount less then 1" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(amount = 0)))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.writeOff.2.error.writeOff.amount.value"
    }

    "return an error if a write off has an amount greater then 99999999" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(amount = 100000000)))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 2
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.unbalanced"
      errors.last.errorMessageKey shouldBe "loan.1.writeOff.2.error.writeOff.amount.value"
    }

    "return no errors if a writeoff has a amount between 1 and 9999999 characters" in {
      val l2pBox = LoansToParticipators(List(
        validLoan.copy(writeOffs = List(validWriteOff.copy(amount = 1))),
        validLoan.copy(writeOffs = List(validWriteOff.copy(amount = 60)))
      ))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "be happy if a writeoff has a valid amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(amountBefore06042016 = validWriteOff.amount - 1)))))

      val errors = l2pBox.validate(boxRetriever)

      errors.size shouldBe 0
    }

    "return an error if a writeoff has an amountBefore06042016 greater then amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(amountBefore06042016 = validWriteOff.amount + 1)))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.writeOff.2.error.writeOff.beforeApril2016Amount.value"
    }

    "return an error if a writeoff has a negative amountBefore06042016" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(amountBefore06042016 = -1)))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.writeOff.2.error.writeOff.beforeApril2016Amount.value"
    }

    "return an error if a write off has a date > 9 months after current AP End Date and no apEndDate" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(date = currentAPEndDate.plusMonths(10), endDateOfAP = None)))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.last.errorMessageKey shouldBe "loan.1.writeOff.2.error.writeOff.apEndDate.required"
    }

    "return no error if a write off has a date <= 9 months after current AP End Date and no apEndDate" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(date = currentAPEndDate.plusMonths(9), endDateOfAP = None)))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "return no errors if a write off has a end date of AP after the current accounting period end date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(endDateOfAP = Some(LocalDate.now().plusDays(200)))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }
  }

  "global Loan validation" should {

    "not allow repaidWithin9Months amount to exceed Loan Amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amount = 200,
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amount = 201))
      )))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.unbalanced"
    }

    "not allow total repaidAfter9Months amount to exceed Loan Amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amount = 200,
        otherRepayments = List(validRepaymentAfter9Months.copy(amount = 201))
      )))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.unbalanced"
    }

    "not allow total writeOffs amount to exceed Loan Amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amount = 200,
        writeOffs = List(validWriteOff.copy(amount = 201))
      )))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.unbalanced"
    }

    "not allow total of repayments and write offs amounts to exceed Loan Amount" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amount = 200,
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amount = 50)),
        otherRepayments = List(validRepaymentAfter9Months.copy(amount = 100)),
        writeOffs = List(validWriteOff.copy(amount = 51))
      )))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.unbalanced"
    }

    "allow Loan with amount equal to total of repayments and write offs" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(
        amount = 200,
        repaymentWithin9Months = Some(validRepaymentWithin9Months.copy(amount = 50)),
        otherRepayments = List(validRepaymentAfter9Months.copy(amount = 100)),
        writeOffs = List(validWriteOff.copy(amount = 50))
      )))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
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
        override def retrieveLPQ03(): LPQ03 = LPQ03(Some(false))
      }

      val errors = l2pBox.validate(testRetriever)
      errors.size shouldBe 0
    }
  }

}
