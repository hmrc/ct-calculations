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

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{MockFrs102AccountsRetriever, AccountsMoneyValidationFixture, AC205}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC7210ASpec extends WordSpec with MockitoSugar with Matchers with MockFrs102AccountsRetriever with AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] {

  def testBasicMoneyValidation(): Unit = {
    when(boxRetriever.ac7200()).thenReturn(AC7200(Some(true)))
    when(boxRetriever.ac7210B()).thenReturn(AC7210B(Some(123)))
    testAccountsMoneyValidationWithMin("AC7210A", minValue = 0, AC7210A)
  }

  testBasicMoneyValidation()

  "AC7210A" should {
    "when AC7210B is empty" when {
      "pass validation if AC7210A has a value AC7200 is true" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(true)))
        when(boxRetriever.ac7210B()).thenReturn(AC7210B(None))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210A(Some(1224)).validate(boxRetriever) shouldBe empty
      }
      "pass validation if AC7210A is empty and AC7200 is false" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(false)))
        when(boxRetriever.ac7210B()).thenReturn(AC7210B(None))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210A(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation if AC7210A is empty and AC7200 is empty" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(None))
        when(boxRetriever.ac7210B()).thenReturn(AC7210B(None))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210A(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation if AC7210A has a value AC7200 is false" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(false)))
        when(boxRetriever.ac7210B()).thenReturn(AC7210B(None))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210A(Some(1224)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7210A"), "error.AC7210A.cannot.exist"))
      }
      "fail validation if AC7210A has a value AC7200 is empty" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(None))
        when(boxRetriever.ac7210B()).thenReturn(AC7210B(None))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210A(Some(1224)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7210A"), "error.AC7210A.cannot.exist"))
      }
      "fail validation if AC7210A has no value and AC7200 is true" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(true)))
        when(boxRetriever.ac7210B()).thenReturn(AC7210B(None))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.abridged.additional.dividend.note.one.box.required"))
      }
    }

    "when AC7210B is populated" when {
      "pass validation if AC7210A has a value AC7200 is true" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(true)))
        when(boxRetriever.ac7210B()).thenReturn(AC7210B(Some(1234)))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210A(Some(1224)).validate(boxRetriever) shouldBe empty
      }
      "pass validation if AC7210A is empty and AC7200 is false" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(false)))
        when(boxRetriever.ac7210B()).thenReturn(AC7210B(Some(1234)))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210A(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation if AC7210A is empty and AC7200 is empty" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(None))
        when(boxRetriever.ac7210B()).thenReturn(AC7210B(Some(1234)))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210A(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation if AC7210A has a value AC7200 is false" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(Some(false)))
        when(boxRetriever.ac7210B()).thenReturn(AC7210B(Some(1234)))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210A(Some(1224)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7210A"), "error.AC7210A.cannot.exist"))
      }
      "fail validation if AC7210A has a value AC7200 is empty" in {
        when(boxRetriever.ac7200()).thenReturn(AC7200(None))
        when(boxRetriever.ac7210B()).thenReturn(AC7210B(Some(1234)))
        when(boxRetriever.ac205()).thenReturn(AC205(Some(new LocalDate(2015, 12, 1))))
        AC7210A(Some(1224)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC7210A"), "error.AC7210A.cannot.exist"))
      }
    }
  }
}
