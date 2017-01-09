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
import uk.gov.hmrc.ct.accounts.MockFrs102AccountsRetriever
import uk.gov.hmrc.ct.accounts.frs102.calculations.RevaluationReserveCalculator
import uk.gov.hmrc.ct.box.CtValidation

class AC190Spec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever {

  "AC190" should {
    "fail validation when calculated value is not equal to AC76" in {
      when(boxRetriever.ac76()).thenReturn(AC76(Some(123)))
      AC190(Some(125)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.AC190.mustEqual.AC76"))
    }

    "fail validation when calculated value is not equal to empty AC76" in {
      when(boxRetriever.ac76()).thenReturn(AC76(None))
      when(boxRetriever.ac77()).thenReturn(AC77(Some(1)))
      AC190(Some(125)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.AC190.mustEqual.AC76"))
    }

    "pass validation when calculated value is equal to 0 and AC76 is empty" in {
      when(boxRetriever.ac76()).thenReturn(AC76(None))
      when(boxRetriever.ac77()).thenReturn(AC77(Some(0)))
      AC190(Some(0)).validate(boxRetriever) shouldBe Set.empty
    }

    "pass validation when calculated value is equal to 0 and AC76 is 0" in {
      when(boxRetriever.ac76()).thenReturn(AC76(Some(0)))
      when(boxRetriever.ac77()).thenReturn(AC77(Some(0)))
      AC190(Some(0)).validate(boxRetriever) shouldBe Set.empty
    }

    "pass validation when calculated value is not equal to empty AC76 and AC77 is empty" in {
      when(boxRetriever.ac76()).thenReturn(AC76(None))
      when(boxRetriever.ac77()).thenReturn(AC77(None))
      AC190(Some(125)).validate(boxRetriever) shouldBe Set.empty
    }

    "calculate the value when both AC77 and AC189 are set" in new RevaluationReserveCalculator {
      calculateAC190(AC76(Some(1)), AC77(Some(10)), AC189(Some(3))).value shouldEqual Some(13)
    }

    "calculate the value when AC77 is set" in new RevaluationReserveCalculator {
      calculateAC190(AC76(Some(1)), AC77(Some(10)), AC189(None)).value shouldEqual Some(10)
    }

    "return 0 when AC76 is set and AC77 and AC189 are not set" in new RevaluationReserveCalculator {
      calculateAC190(AC76(Some(1)), AC77(None), AC189(None)).value shouldEqual Some(0)
    }

    "return None when AC76, AC77 and AC189 are not set" in new RevaluationReserveCalculator {
      calculateAC190(AC76(None), AC77(None), AC189(None)).value shouldEqual None
    }
  }
}
