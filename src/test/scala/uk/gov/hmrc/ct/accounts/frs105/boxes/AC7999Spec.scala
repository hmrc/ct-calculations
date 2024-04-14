/*
 * Copyright 2024 HM Revenue & Customs
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

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.utils.AdditionalNotesAndFootnotesHelper
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs105AccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox.StandardCohoTextFieldLimit

class AC7999Spec extends AdditionalNotesAndFootnotesHelper with AccountsFreeTextValidationFixture[Frs105AccountsBoxRetriever] with MockFrs105AccountsRetriever {

  private def validateAC7999(inputField: Option[String], validationResult: Set[CtValidation]) = AC7999(inputField).validate(boxRetriever) shouldBe validationResult

   override val boxId: String = "AC7999"

    "validation should pass successfully" when {
      "'Yes' button has been pressed and there is content in the text field" in {
        when(boxRetriever.ac7999a()) thenReturn AC7999a(Some(true))
        validateAC7999(Some(input), validationSuccess)
      }
      "'No' button has been pressed and there is content in the text field" in {
        when(boxRetriever.ac7999a()) thenReturn AC7999a(Some(false))
        validateAC7999(Some(input), validationSuccess)
      }
    }

      "validation should fail successfully" when {
      "'Yes' button has been pressed and there is no content in the text field" in {
        when(boxRetriever.ac7999a()) thenReturn AC7999a(Some(true))
        validateAC7999(Some(""), fieldRequiredError(boxId))
        validateAC7999(None, fieldRequiredError(boxId))
       }
      }

      "the string entered contains more than 20,000" in {
        when(boxRetriever.ac7999a()) thenReturn AC7999a(Some(true))
        val input = "a" * StandardCohoTextFieldLimit + 1
        val tooManyCharactersErrorMsg = Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(List("1", "20000"))))
        validateAC7999(Some(input), tooManyCharactersErrorMsg)
      }


}
