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
import uk.gov.hmrc.ct.accounts.{AccountsFreeTextValidationFixture, MockAbridgedAccountsRetriever, MockFrs102AccountsRetriever, MockFullAccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC5064ATextSpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsFreeTextValidationFixture[Frs102AccountsBoxRetriever] {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac64()).thenReturn(AC64(Some(100)))
  }

  testTextFieldValidation("AC5064A", AC5064A, testUpperLimit = Some(StandardCohoTextFieldLimit))
  testTextFieldIllegalCharacterValidationReturnsIllegalCharacters("AC5064A", AC5064A)

}

class AC5064AAbridgedSpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockAbridgedAccountsRetriever {

  "AC5064A" should {
    "fail validation when populated and AC64 is empty" in {
      when(boxRetriever.ac64()).thenReturn(AC64(None))
      when(boxRetriever.ac65()).thenReturn(AC65(None))
      AC5064A(Some("testing")).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
    }
  }

}

class AC5064AFullSpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFullAccountsRetriever {

  private def setupEmpty(): Unit = {
    when(boxRetriever.ac156()).thenReturn(AC156(None))
    when(boxRetriever.ac157()).thenReturn(AC157(None))
    when(boxRetriever.ac158()).thenReturn(AC158(None))
    when(boxRetriever.ac159()).thenReturn(AC159(None))
    when(boxRetriever.ac160()).thenReturn(AC160(None))
    when(boxRetriever.ac161()).thenReturn(AC161(None))
    when(boxRetriever.ac162()).thenReturn(AC162(None))
    when(boxRetriever.ac163()).thenReturn(AC163(None))
    when(boxRetriever.ac5064A()).thenReturn(AC5064A(None))
  }

  private def setupNonEmpty(): Unit = {
    when(boxRetriever.ac156()).thenReturn(AC156(Some(1)))
    when(boxRetriever.ac157()).thenReturn(AC157(Some(1)))
    when(boxRetriever.ac158()).thenReturn(AC158(Some(1)))
    when(boxRetriever.ac159()).thenReturn(AC159(Some(1)))
    when(boxRetriever.ac160()).thenReturn(AC160(Some(1)))
    when(boxRetriever.ac161()).thenReturn(AC161(Some(1)))
    when(boxRetriever.ac162()).thenReturn(AC162(Some(1)))
    when(boxRetriever.ac163()).thenReturn(AC163(Some(1)))
    when(boxRetriever.ac5064A()).thenReturn(AC5064A(Some("test")))
  }

  "AC5064A" should {
    "return cannot exist validation" when {
      "fail validation when AC156 populated and AC64 is empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(None))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac156()).thenReturn(AC156(Some(1)))
        AC5064A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
      }
      "fail validation when AC157 populated and AC64 is empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(None))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac157()).thenReturn(AC157(Some(1)))
        AC5064A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
      }
      "fail validation when AC158 populated and AC64 is empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(None))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac158()).thenReturn(AC158(Some(1)))
        AC5064A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
      }
      "fail validation when AC159 populated and AC64 is empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(None))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac159()).thenReturn(AC159(Some(1)))
        AC5064A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
      }
      "fail validation when AC160 populated and AC64 is empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(None))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac160()).thenReturn(AC160(Some(1)))
        AC5064A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
      }
      "fail validation when AC161 populated and AC64 is empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(None))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac161()).thenReturn(AC161(Some(1)))
        AC5064A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
      }
      "fail validation when AC162 populated and AC64 is empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(None))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac162()).thenReturn(AC162(Some(1)))
        AC5064A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
      }
      "fail validation when AC163 populated and AC64 is empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(None))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac163()).thenReturn(AC163(Some(1)))
        AC5064A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
      }

      "fail validation when populated and AC64 is empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(None))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac5064A()).thenReturn(AC5064A(Some("testing")))
        AC5064A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.cannotExist"))
      }

      "pass validation when not populated and AC64 is empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(None))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        AC5064A(None).validate(boxRetriever) shouldBe Set.empty
      }
    }

    "return must not be empty validation error" when {
      "fail validation when not populated and AC64 is not empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(Some(10)))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        AC5064A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.creditorsAfterOneYear.mustNotBeEmpty"))
      }
      "pass validation when AC156 is populated and AC64 is not empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(Some(10)))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac156()).thenReturn(AC156(Some(10)))
        AC5064A(None).validate(boxRetriever) shouldBe Set.empty
      }
      "pass validation when AC157 is populated and AC64 is not empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(Some(10)))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac157()).thenReturn(AC157(Some(10)))
        AC5064A(None).validate(boxRetriever) shouldBe Set.empty
      }
      "pass validation when AC158 is populated and AC64 is not empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(Some(10)))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac158()).thenReturn(AC158(Some(10)))
        AC5064A(None).validate(boxRetriever) shouldBe Set.empty
      }
      "pass validation when AC159 is populated and AC64 is not empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(Some(10)))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac159()).thenReturn(AC159(Some(10)))
        AC5064A(None).validate(boxRetriever) shouldBe Set.empty
      }
      "pass validation when AC160 is populated and AC64 is not empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(Some(10)))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac160()).thenReturn(AC160(Some(10)))
        AC5064A(None).validate(boxRetriever) shouldBe Set.empty
      }
      "pass validation when AC161 is populated and AC64 is not empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(Some(10)))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac161()).thenReturn(AC161(Some(10)))
        AC5064A(None).validate(boxRetriever) shouldBe Set.empty
      }
      "pass validation when AC162 is populated and AC64 is not empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(Some(10)))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac162()).thenReturn(AC162(Some(10)))
        AC5064A(None).validate(boxRetriever) shouldBe Set.empty
      }
      "pass validation when AC163 is populated and AC64 is not empty" in {
        setupEmpty()
        when(boxRetriever.ac64()).thenReturn(AC64(Some(10)))
        when(boxRetriever.ac65()).thenReturn(AC65(None))
        when(boxRetriever.ac163()).thenReturn(AC163(Some(10)))
        AC5064A(None).validate(boxRetriever) shouldBe Set.empty
      }
    }

  }

}
