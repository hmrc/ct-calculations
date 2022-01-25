/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito.when
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.utils.AdditionalNotesAndFootnotesHelper
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox.StandardCohoTextFieldLimit


class AC200Spec extends AdditionalNotesAndFootnotesHelper with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  private def validateAC200(inputField: Option[String], validationResult: Set[CtValidation]) = AC200(inputField).validate(boxRetriever) shouldBe validationResult

  override val boxId: String = "AC200"

  "validation should pass successfully" when {
    "'Yes' button has been pressed and there is content in the text field" in {
      when(boxRetriever.ac200a()) thenReturn AC200A(Some(true))
      validateAC200(Some(input), validationSuccess)
    }
    "'No' button has been pressed and there is content in the text field" in {
      when(boxRetriever.ac200a()) thenReturn AC200A(Some(false))
      validateAC200(Some(input), validationSuccess)
    }
  }

  "validation should fail successfully" when {
    "'Yes' button has been pressed and there is no content in the text field" in {
      when(boxRetriever.ac200a()) thenReturn AC200A(Some(true))
      validateAC200(Some(""), fieldRequiredError(boxId))
      validateAC200(None, fieldRequiredError(boxId))
    }


    "the string entered contains more than 20,000" in {
      when(boxRetriever.ac200a()) thenReturn AC200A(Some(true))
      val input = "a" * StandardCohoTextFieldLimit + 1
      val tooManyCharactersErrorMsg = Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(List("1", "20000"))))
      validateAC200(Some(input), tooManyCharactersErrorMsg)
    }
  }
  "the string only contains whitespace characters" in {
    when(boxRetriever.ac200a()) thenReturn AC200A(Some(true))
    validateAC200(Some(" "), fieldRequiredError(boxId))
  }
}
