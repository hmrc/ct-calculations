/*
 * Copyright 2015 HM Revenue & Customs
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
import uk.gov.hmrc.ct.computations.{CP2, AP2}
import uk.gov.hmrc.ct.ct600.v3.stubs.StubbedCT600BoxRetriever
import uk.gov.hmrc.ct.ct600a.v3.{WriteOff, Loan, LoansToParticipators}

class LoansToParticipatorsSpec extends WordSpec with Matchers {

  val currentAPEndDate = new LocalDate(2015, 6, 1)

  val boxRetriever = new StubbedCT600BoxRetriever {
    override def retrieveCP2(): CP2 = CP2(currentAPEndDate)
  }

  val validLoan = Loan(id = "1",
    name = "Smurfette",
    amount = 200,
    isRepaidWithin9Months = Some(false),
    isRepaidAfter9Months = Some(false),
    hasWriteOffs = Some(false))

  val validWriteOff = WriteOff(
    id = "2",
    date = currentAPEndDate.plusDays(2),
    amount = 2000,
    endDateOfAP = Some(currentAPEndDate.plusDays(1))
  )

  "LoansToParticipators validate" should {

    "not return any errors for an empty loans list" in {
      val l2pBox = LoansToParticipators(List.empty)

      l2pBox.validate(boxRetriever) shouldBe Set.empty
    }

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

    "return no errors if a loan has a amount between 1 and 9999999 characters" in {
      val l2pBox = LoansToParticipators(List(
        validLoan.copy(amount = 1),
        validLoan.copy(amount = 9999999)
      ))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "return an error if isRepaidWithin9Months value not provided" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(isRepaidWithin9Months = None)))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.isRepaidWithin9Months.required"
    }

    "be happy if isRepaidWithin9Months value is true" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(isRepaidWithin9Months = Some(true))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "be happy if isRepaidWithin9Months value is false" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(isRepaidWithin9Months = Some(false))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "return an error if isRepaidAfter9Months value not provided" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(isRepaidAfter9Months = None)))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.isRepaidAfter9Months.required"
    }

    "be happy if isRepaidAfter9Months value is true" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(isRepaidAfter9Months = Some(true))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "be happy if isRepaidAfter9Months value is false" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(isRepaidAfter9Months = Some(false))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "return an error if hasWriteOffs value not provided" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(hasWriteOffs = None)))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.error.loan.hasWriteOffs.required"
    }

    "be happy if hasWriteOffs value is true" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(hasWriteOffs = Some(true))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "be happy if hasWriteOffs value is false" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(hasWriteOffs = Some(false))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

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
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.1.writeOff.2.error.writeOff.amount.value"
    }

    "return no errors if a writeoff has a amount between 1 and 9999999 characters" in {
      val l2pBox = LoansToParticipators(List(
        validLoan.copy(writeOffs = List(validWriteOff.copy(amount = 1))),
        validLoan.copy(writeOffs = List(validWriteOff.copy(amount = 99999999)))
      ))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "return no errors if a write off has a end date of AP after the current accounting period end date" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(writeOffs = List(validWriteOff.copy(endDateOfAP = Some(LocalDate.now().plusDays(200)))))))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }
  }

}
