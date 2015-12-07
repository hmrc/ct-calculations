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

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.ct600.v3.stubs.StubbedCT600BoxRetriever
import uk.gov.hmrc.ct.ct600a.v3.{Loan, LoansToParticipators}

class LoansToParticipatorsSpec extends WordSpec with Matchers {

  val validLoan = Loan("1", "Smurfette", 200, isRepaidWithin9Months = Some(true))

  val boxRetriever = new StubbedCT600BoxRetriever {}

  "LoansToParticipators validate" should {

    "not return any errors for an empty loans list" in {
      val l2pBox = LoansToParticipators(List.empty)

      l2pBox.validate(boxRetriever) shouldBe Set.empty
    }

    "return an error if a loan has a name shorter then 2 characters" in {

      val l2pBox = LoansToParticipators(List(validLoan.copy(name = "a")))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("loan.1")
      errors.head.errorMessageKey shouldBe "loan.1.error.name.length"
    }

    "return an error if a loan has a name greater then 56 characters" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(name = "PneumonoultramicroscopicsilicovolcanoconiosisisgoodformeX")))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("loan.1")
      errors.head.errorMessageKey shouldBe "loan.1.error.name.length"
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
      errors.head.boxId shouldBe Some("loan.1")
      errors.head.errorMessageKey shouldBe "loan.1.error.amount.value"
    }

    "return an error if a loan has an amount greater then 99999999" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(amount = 100000000)))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("loan.1")
      errors.head.errorMessageKey shouldBe "loan.1.error.amount.value"
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
      errors.head.boxId shouldBe Some("loan.1")
      errors.head.errorMessageKey shouldBe "loan.1.error.isRepaidWithin9Months.required"
    }

    "be happy if isRepaidWithin9Months value is true" in {
      val l2pBox = LoansToParticipators(List(validLoan))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "be happy if isRepaidWithin9Months value is false" in {
      val l2pBox = LoansToParticipators(List(validLoan.copy(isRepaidWithin9Months = Some(false))))
      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }
  }

}
