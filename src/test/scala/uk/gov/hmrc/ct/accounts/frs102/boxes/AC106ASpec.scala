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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.utils.AdditionalNotesAndFootnotesHelper
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC106ASpec extends AdditionalNotesAndFootnotesHelper with MockFrs102AccountsRetriever with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC106A", AC106A)

  private def validateAC106A(inputField: Option[String], validationResult: Set[CtValidation]) = AC106A(inputField).validate(boxRetriever) shouldBe validationResult
  override val boxId: String = "AC106A"

  "AC106A" should {

    "not validate with any errors when AC106A has a value" in {
      validateAC106A(Some("Have employed Chuck Norris as CEO"), validationSuccess)
    }

    "not validate with any errors and AC106A has no value" in {
      validateAC106A(None, validationSuccess)
    }

    "the string entered contains more than 20,000" in {
      val input = "a" * StandardCohoTextFieldLimit + 1
      val tooManyCharactersErrorMsg = Set(CtValidation(Some(boxId), s"error.$boxId.text.sizeRange", Some(List("1", "20000"))))
      validateAC106A(Some(input), tooManyCharactersErrorMsg)
    }
  }
}
