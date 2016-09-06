/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs10x.abridged

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.{AccountsMoneyValidationFixture, MockAbridgedAccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC5076ASpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockAbridgedAccountsRetriever
  with AccountsMoneyValidationFixture {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac76()).thenReturn(AC76(Some(100)))
    when(boxRetriever.ac5076A()).thenReturn(AC5076A(Some(10)))
    when(boxRetriever.ac5076B()).thenReturn(AC5076B(Some(10)))
    when(boxRetriever.ac5076C()).thenReturn(AC5076C(Some("Test content")))
  }

  testAccountsMoneyValidationWithMinMax("AC5076A", STANDARD_MIN, STANDARD_MAX, AC5076A, testEmpty = false)

  "AC5076A" should {
    "fail validation when AC76 isn't empty and this box is empty" in {
      when(boxRetriever.ac76()).thenReturn(AC76(Some(10)))
      AC5076A(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5076A"), "error.AC5076A.required"))
    }

    "throw global error when note cannot be entered" in {
      val box = AC5076A(Some(10))
      when(boxRetriever.ac76()).thenReturn(AC76(None))
      when(boxRetriever.ac5076A()).thenReturn(box)

      box.validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.revaluationReserveNote.cannot.exist"))
    }

    "validate successfully if note can't be entered but is empty" in {
      val box = AC5076A(None)

      when(boxRetriever.ac76()).thenReturn(AC76(None))
      when(boxRetriever.ac5076A()).thenReturn(box)
      when(boxRetriever.ac5076B()).thenReturn(AC5076B(None))
      when(boxRetriever.ac5076C()).thenReturn(AC5076C(None))

      box.validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully if note can be entered" in {
      AC5076A(Some(10)).validate(boxRetriever) shouldBe Set.empty
    }
  }
}
