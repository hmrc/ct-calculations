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
      val nameTooShortLoan = Loan("a", 200)
      val nameFineLoan = Loan("abba", 200)

      val l2pBox = LoansToParticipators(List(nameTooShortLoan, nameFineLoan))

      val errors = l2pBox.validate(boxRetriever)
      errors.size shouldBe 1
      errors.head.boxId shouldBe Some("LoansToParticipators")
      errors.head.errorMessageKey shouldBe "loan.a.error.loanNameLength"
    }

  }

}
