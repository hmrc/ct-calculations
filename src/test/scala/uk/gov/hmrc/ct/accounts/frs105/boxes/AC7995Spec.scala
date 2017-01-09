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

class AC7995Spec extends WordSpec with Matchers with MockitoSugar with AccountsFreeTextValidationFixture[Frs105AccountsBoxRetriever] with MockFrs105AccountsRetriever {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac7991()).thenReturn(AC7991(Some(true)))
  }

  testTextFieldValidation("AC7995", AC7995, testLowerLimit = Some(1), testUpperLimit = Some(StandardCohoTextFieldLimit), testMandatory = Some(true))
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC7995", AC7995)

  "given AC7991 is not true AC7995 " should {

    "fail validation when populated if AC7991 is false" in {
      when(boxRetriever.ac7991()).thenReturn(AC7991(Some(false)))

      AC7995(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7995"), "error.AC7995.cannot.exist", None))
      AC7995(Some("%&^%./[]")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7995"), "error.AC7995.cannot.exist", None))
      AC7995(Some("legal content")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7995"), "error.AC7995.cannot.exist", None))
    }

    "fail validation when populated if AC7991 not set" in {
      when(boxRetriever.ac7991()).thenReturn(AC7991(None))

      AC7995(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7995"), "error.AC7995.cannot.exist", None))
      AC7995(Some("%&^%./[]")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7995"), "error.AC7995.cannot.exist", None))
      AC7995(Some("legal content")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7995"), "error.AC7995.cannot.exist", None))
    }

    "pass validation if empty and AC7991 not set" in {
      when(boxRetriever.ac7991()).thenReturn(AC7991(None))

      AC7995(None).validate(boxRetriever) shouldBe empty
    }

    "pass validation when not populated if AC7991 false" in {
      when(boxRetriever.ac7991()).thenReturn(AC7991(Some(false)))

      AC7995(None).validate(boxRetriever) shouldBe empty
    }

  }

  "given AC7991 is true AC7995 " should {

    "fail validation when empty" in {
      when(boxRetriever.ac7991()).thenReturn(AC7991(Some(true)))

      AC7995(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7995"), "error.AC7995.required", None))
    }
  }

}
