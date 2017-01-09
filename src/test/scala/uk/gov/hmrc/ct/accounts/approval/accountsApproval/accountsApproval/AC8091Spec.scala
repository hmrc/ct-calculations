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

package uk.gov.hmrc.ct.accounts.approval.accountsApproval.accountsApproval

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.approval.boxes.AC8091
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.{AbridgedAccountsBoxRetriever, Frs102AccountsBoxRetriever}
import uk.gov.hmrc.ct.box.CtValidation

class AC8091Spec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {


  "AC8091 validate" should {
    "return errors when AC8091 is empty" in {
      val mockBoxRetriever = mock[AbridgedAccountsBoxRetriever]

      AC8091(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8091"), "error.AC8091.required"))
    }

    "return errors when AC8091 is false" in {
      val mockBoxRetriever = mock[AbridgedAccountsBoxRetriever]

      AC8091(Some(false)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8091"), "error.AC8091.required"))
    }

    "return value when AC8091 is true" in {
      val mockBoxRetriever = mock[AbridgedAccountsBoxRetriever]

      AC8091(Some(true)).value shouldBe Some(true)
    }
  }
}
