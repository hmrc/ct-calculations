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
import uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations.RevaluationReserveCalculator
import uk.gov.hmrc.ct.accounts.frs10x.MockAbridgedAccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC5076BSpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockAbridgedAccountsRetriever {

  "AC5076B" should {
    "fail validation when calculated value is not equal to AC76" in {
      when(boxRetriever.ac76()).thenReturn(AC76(Some(123)))
      AC5076B(Some(125)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5076B"), "error.AC5076B.mustEqual.AC76"))
    }

    "calculate the value when both AC77 and AC5076A are set" in new RevaluationReserveCalculator {
      calculateAC5076B(AC77(Some(10)), AC5076A(Some(3))).value shouldEqual Some(13)
    }

    "calculate the value when AC77 is set" in new RevaluationReserveCalculator {
      calculateAC5076B(AC77(Some(10)), AC5076A(None)).value shouldEqual Some(10)
    }

    "return None when both AC77 and AC5076A are not set" in new RevaluationReserveCalculator {
      calculateAC5076B(AC77(None), AC5076A(None)).value shouldEqual None
    }
  }
}
