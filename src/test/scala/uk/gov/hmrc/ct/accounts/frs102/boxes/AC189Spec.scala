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
import uk.gov.hmrc.ct.accounts.{MockFrs102AccountsRetriever, AccountsMoneyValidationFixture}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC189Spec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac76()).thenReturn(AC76(Some(100)))
    when(boxRetriever.ac189()).thenReturn(AC189(Some(10)))
    when(boxRetriever.ac190()).thenReturn(AC190(Some(10)))
    when(boxRetriever.ac5076C()).thenReturn(AC5076C(Some("Test content")))
  }

  testAccountsMoneyValidationWithMinMax("AC189", STANDARD_MIN, STANDARD_MAX, AC189, testEmpty = false)

  "AC189" should {
    "fail validation when AC76 isn't empty and this box is empty" in {
      when(boxRetriever.ac76()).thenReturn(AC76(Some(10)))
      AC189(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC189"), "error.AC189.required"))
    }

    "throw global error when note cannot be entered" in {
      val box = AC189(Some(10))
      when(boxRetriever.ac76()).thenReturn(AC76(None))
      when(boxRetriever.ac77()).thenReturn(AC77(None))
      when(boxRetriever.ac189()).thenReturn(box)

      box.validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.revaluationReserveNote.cannot.exist"))
    }

    "validate successfully if note can't be entered but is empty" in {
      val box = AC189(None)

      when(boxRetriever.ac76()).thenReturn(AC76(None))
      when(boxRetriever.ac77()).thenReturn(AC77(None))
      when(boxRetriever.ac189()).thenReturn(box)
      when(boxRetriever.ac190()).thenReturn(AC190(None))
      when(boxRetriever.ac5076C()).thenReturn(AC5076C(None))

      box.validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully if note can be entered" in {
      AC189(Some(10)).validate(boxRetriever) shouldBe Set.empty
    }
  }
}
