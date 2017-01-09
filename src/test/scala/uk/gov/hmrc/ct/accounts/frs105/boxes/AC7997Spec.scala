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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs105AccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC7997Spec extends WordSpec with Matchers with MockitoSugar with AccountsFreeTextValidationFixture[Frs105AccountsBoxRetriever] with MockFrs105AccountsRetriever {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac7992()).thenReturn(AC7992(Some(true)))
  }

  testTextFieldValidation("AC7997", AC7997, testLowerLimit = Some(1), testUpperLimit = Some(StandardCohoTextFieldLimit), testMandatory = Some(true))
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC7997", AC7997)

  "given AC7992 is not true AC7997 " should {

    "fail validation when populated if AC7992 is false" in {
      when(boxRetriever.ac7992()).thenReturn(AC7992(Some(false)))

      AC7997(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7997"), "error.AC7997.cannot.exist", None))
      AC7997(Some("%&^%./[]")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7997"), "error.AC7997.cannot.exist", None))
      AC7997(Some("legal content")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7997"), "error.AC7997.cannot.exist", None))
    }

    "fail validation when populated if AC7992 not set" in {
      when(boxRetriever.ac7992()).thenReturn(AC7992(None))

      AC7997(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7997"), "error.AC7997.cannot.exist", None))
      AC7997(Some("%&^%./[]")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7997"), "error.AC7997.cannot.exist", None))
      AC7997(Some("legal content")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7997"), "error.AC7997.cannot.exist", None))
    }

    "pass validation if empty and AC7992 not set" in {
      when(boxRetriever.ac7992()).thenReturn(AC7992(None))

      AC7997(None).validate(boxRetriever) shouldBe empty
    }

    "pass validation when not populated if AC7992 false" in {
      when(boxRetriever.ac7992()).thenReturn(AC7992(Some(false)))

      AC7997(None).validate(boxRetriever) shouldBe empty
    }

  }

  "given AC7992 is true AC7997 " should {

    "fail validation when empty" in {
      when(boxRetriever.ac7992()).thenReturn(AC7992(Some(true)))

      AC7997(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7997"), "error.AC7997.required", None))
    }
  }

}
