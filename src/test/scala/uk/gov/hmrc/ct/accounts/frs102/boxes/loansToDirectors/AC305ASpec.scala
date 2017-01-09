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

package uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC305ASpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  testTextFieldValidation("AC305A", AC305A, testLowerLimit = Some(1), testUpperLimit = Some(250), testMandatory = Some(true))
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC305A", AC305A)

  "AC305A should" should {

    "fail validation when not set" in {
      AC305A(None).validate(null) shouldBe Set(CtValidation(Some("AC305A"), "error.AC305A.required", None))
    }
  }
}
