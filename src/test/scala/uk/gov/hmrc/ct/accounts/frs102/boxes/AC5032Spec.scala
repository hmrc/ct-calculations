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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC5032Spec extends WordSpec with MockitoSugar with Matchers with MockFrs102AccountsRetriever with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac32()).thenReturn(AC32(Some(4)))
    when(boxRetriever.ac33()).thenReturn(AC33(Some(4)))
  }

  testTextFieldValidation("AC5032", AC5032, testUpperLimit = Some(StandardCohoTextFieldLimit))
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC5032", AC5032)

  "AC5032" should {

    "pass validation when populated and AC32 is empty" in {
      when(boxRetriever.ac32()).thenReturn(AC32(None))
      when(boxRetriever.ac33()).thenReturn(AC33(Some(4)))
      AC5032(Some("testing")).validate(boxRetriever) shouldBe Set.empty
    }

    "pass validation when populated and AC33 is empty" in {
      when(boxRetriever.ac32()).thenReturn(AC32(Some(4)))
      when(boxRetriever.ac33()).thenReturn(AC33(None))
      AC5032(Some("testing")).validate(boxRetriever) shouldBe Set.empty
    }

    "fail validation when populated and AC32 and AC33 are empty" in {
      when(boxRetriever.ac32()).thenReturn(AC32(None))
      when(boxRetriever.ac33()).thenReturn(AC33(None))
      AC5032(Some("testing")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5032"), "error.AC5032.cannot.exist"))
    }

  }
}
