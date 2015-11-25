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

  val boxRetriever = new StubbedCT600BoxRetriever {}

  "LoansToParticipators validate" should {

    "not return any errors for an empty loans list" in {
      val l2pBox = LoansToParticipators(List.empty)

      l2pBox.validate(boxRetriever) shouldBe Set.empty
    }

    "return an error if a loan has a name shorter then 2 characters" in {
      val nameTooShortLoan = Loan("1", "a", 200)

      val l2pBox = LoansToParticipators(List(nameTooShortLoan))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("loan.1.name")
      errors.head.errorMessageKey shouldBe "loan.1.error.loanNameLength"
    }

    "return an error if a loan has a name greater then 56 characters" in {
      val nameTooShortLoan = Loan("1", "PneumonoultramicroscopicsilicovolcanoconiosisisgoodformeX", 200)

      val l2pBox = LoansToParticipators(List(nameTooShortLoan))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("loan.1.name")
      errors.head.errorMessageKey shouldBe "loan.1.error.loanNameLength"
    }

    "return no errors if a loan has a name between 2 and 56 characters" in {
      val nameShortFineLoan = Loan("1", "ab", 200)
      val nameLongFineLoan = Loan("2", "Pneumonoultramicroscopicsilicovolcanoconiosisisgoodforme", 200)

      val l2pBox = LoansToParticipators(List(nameShortFineLoan, nameLongFineLoan))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }

    "return an error if a loan has an amount less then 1" in {
      val amountZeroLoan = Loan("1", "Gargamel", 0)

      val l2pBox = LoansToParticipators(List(amountZeroLoan))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("loan.1.amount")
      errors.head.errorMessageKey shouldBe "loan.1.error.loanAmount"
    }

    "return an error if a loan has an amount greater then 99999999" in {
      val amountZeroLoan = Loan("1", "Gargamel", 100000000)

      val l2pBox = LoansToParticipators(List(amountZeroLoan))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("loan.1.amount")
      errors.head.errorMessageKey shouldBe "loan.1.error.loanAmount"
    }

    "return no errors if a loan has a amount between 1 and 9999999 characters" in {
      val amountShortFineLoan = Loan("1", "Smurfette", 1)
      val amountLongFineLoan = Loan("2", "Papa Smurf", 9999999)

      val l2pBox = LoansToParticipators(List(amountShortFineLoan, amountLongFineLoan))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 0
    }
  }

}
