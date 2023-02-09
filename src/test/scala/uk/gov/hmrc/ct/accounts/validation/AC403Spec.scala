/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.validation

import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AC401, AC403}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC403Spec extends WordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[AccountsBoxRetriever]

  "AC403 validation" should {
    "show error if AC403 is larger than AC401" in {
      when(boxRetriever.ac401())thenReturn AC401(Some(1000))
      val validationResult = AC403(10001).validate(boxRetriever)

      validationResult shouldBe Set(CtValidation(Some("AC403"),"error.AC403.exceeds.AC401"))
    }

    "show error if AC403 isnt present when AC401 exists" in {
      when(boxRetriever.ac401())thenReturn AC401(Some(1))
      val validationResult = AC403(None).validate(boxRetriever)

      validationResult shouldBe Set(CtValidation(Some("AC403"),"error.AC403.required"))
    }
  }
}
