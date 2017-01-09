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

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}

class AC320ASpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac320()).thenReturn(AC320(Some(false)))
    when(boxRetriever.ac320A()).thenReturn(AC320A(Some("text")))
  }

  testTextFieldValidation("AC320A", AC320A, testUpperLimit = Some(StandardCohoTextFieldLimit), testMandatory = None)
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC320A", AC320A)

  "AC320A" should {
    "fail validation when not empty and AC320 is true" in {

      when(boxRetriever.ac320()).thenReturn(AC320(Some(true)))

      AC320A(Some("text")).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC320A"), "error.AC320A.cannot.exist"))
    }

    "fail validation when empty and AC320 is false" in {

      when(boxRetriever.ac320()).thenReturn(AC320(Some(false)))

      AC320A(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC320A"), "error.AC320A.required"))
    }

    "pass validation when empty and AC320 is None" in {

      when(boxRetriever.ac320()).thenReturn(AC320(None))

      AC320A(None).validate(boxRetriever) shouldBe empty
    }

    "pass validation when empty and AC320 is true" in {

      when(boxRetriever.ac320()).thenReturn(AC320(Some(true)))

      AC320A(None).validate(boxRetriever) shouldBe empty
    }

    "pass validation when has value and AC320 is false" in {

      when(boxRetriever.ac320()).thenReturn(AC320(Some(false)))

      AC320A(Some("text")).validate(boxRetriever) shouldBe empty
    }
  }
}
