/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.scalatest.{Matchers, WordSpec}
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs105AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC7999Spec extends WordSpec with Matchers with MockitoSugar with AccountsFreeTextValidationFixture[Frs105AccountsBoxRetriever] with MockFrs105AccountsRetriever {

  private val boxID = "AC7999"
  private val minLength = Some(1)
  private val isMandatory = true
  private lazy val yesButton = boxRetriever.ac7999a()
  private lazy val noButton = boxRetriever.ac7999b()

//  when(boxRetriever.ac7999a()) thenReturn AC7999a(Some(true))
//  testTextFieldValidation(boxID, AC7999, minLength, Some(StandardCohoTextFieldLimit), Some(isMandatory))

    "validation should pass successfully" when {
      //      "'No' button has been pressed" in {
      //        when(boxRetriever.ac7999b()) thenReturn AC7999b(Some(true))
      //        AC7999(None).validate(boxRetriever) shouldBe Set.empty
      //      }

      "'Yes' button has been pressed and the text field is non-empty" in {
        when(yesButton) thenReturn AC7999a(Some(true))
        when(noButton) thenReturn AC7999b(Some(false))
        AC7999(Some("many off-balance sheet arrangements")).validate(boxRetriever) shouldBe Set()
      }
      //
      //      "'Yes' button has been pressed and the text field is the minimum length - 1" in {
      //        when(yesButton) thenReturn AC7999a(Some(true))
      //        when(noButton) thenReturn AC7999b(Some(false))
      //        AC7999(Some("m")).validate(boxRetriever) shouldBe Set()
      //      }
          }

      "validation should fail successfully" when {

        "'Yes' button has been pressed and the text field is empty" in {
          when(yesButton) thenReturn AC7999a(Some(true))
          when(noButton) thenReturn AC7999b(Some(false))
          AC7999(None).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxID),"error.AC7999.required", None))
        }
      }
}

// should succeed if you press no.
// should succeed if you press yes and enter in box ac7999.
// should fail if you press nothing
// should fail if you press yes and enter nothing in the text box
// should fail if you write inappropriate characters in the text box