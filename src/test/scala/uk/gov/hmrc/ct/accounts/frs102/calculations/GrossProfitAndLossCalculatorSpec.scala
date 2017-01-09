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

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.AC12
import uk.gov.hmrc.ct.accounts.frs102.boxes._

class GrossProfitAndLossCalculatorSpec extends WordSpec with Matchers {

  "GrossProfitAndLossCalculator" should {
    "calculate AC16 and" when {
      "return empty AC16 if AC12 and AC14 are empty" in new GrossProfitAndLossCalculator {
        calculateAC16(AC12(None), AC14(None)) shouldBe AC16(None)
      }
      "return AC12 value as AC16 if AC12 has value and AC14 is empty" in new GrossProfitAndLossCalculator {
        calculateAC16(AC12(Some(12)), AC14(None)) shouldBe AC16(Some(12))
      }
      "return -AC14 value as AC16 if AC12 is empty and AC14 has a value" in new GrossProfitAndLossCalculator {
        calculateAC16(AC12(None), AC14(Some(14))) shouldBe AC16(Some(-14))
      }
      "return AC12 - AC14 value as AC16 if AC12 and AC14 have values" in new GrossProfitAndLossCalculator {
        calculateAC16(AC12(Some(12)), AC14(Some(14))) shouldBe AC16(Some(-2))
      }
    }

    "calculate AC17 and" when {
      "return empty AC17 if AC12 and AC14 are empty" in new GrossProfitAndLossCalculator {
        calculateAC17(AC13(None), AC15(None)) shouldBe AC17(None)
      }
      "return AC12 value as AC17 if AC12 has value and AC14 is empty" in new GrossProfitAndLossCalculator {
        calculateAC17(AC13(Some(13)), AC15(None)) shouldBe AC17(Some(13))
      }
      "return -AC14 value as AC17 if AC12 is empty and AC14 has a value" in new GrossProfitAndLossCalculator {
        calculateAC17(AC13(None), AC15(Some(15))) shouldBe AC17(Some(-15))
      }
      "return AC12 - AC14 value as AC17 if AC12 and AC14 have values" in new GrossProfitAndLossCalculator {
        calculateAC17(AC13(Some(13)), AC15(Some(15))) shouldBe AC17(Some(-2))
      }
    }
  }
}
