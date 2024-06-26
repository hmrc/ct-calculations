/*
 * Copyright 2024 HM Revenue & Customs
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

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC13, AC15, AC16, AC17, AC24, AC25}
import uk.gov.hmrc.ct.accounts.{AC12, AC14, AC401, AC402, AC403, AC404}

class GrossProfitAndLossCalculatorSpec extends AnyWordSpec with Matchers {

  "GrossProfitAndLossCalculator" should {
    "calculate AC16 and" when {
      "return empty AC16 if all are empty" in new GrossProfitAndLossCalculator {
        calculateAC16(AC12(None), AC24(None), AC401(None), AC403(None),  AC14(None)) shouldBe AC16(None)
      }
      "return AC12 value as AC16 if AC12 and AC24(None), AC401 has value and AC14 and AC403 are empty" in new GrossProfitAndLossCalculator {
        calculateAC16(AC12(Some(12)), AC24(None), AC401(Some(10)), AC403(None), AC14(None)) shouldBe AC16(Some(22))
      }
      "return -AC14 value as AC16 if AC12 and AC24(None), AC401 are empty and AC14 and AC 403has a value" in new GrossProfitAndLossCalculator {
        calculateAC16(AC12(None), AC24(None), AC401(None), AC403(Some(10)), AC14(Some(14))) shouldBe AC16(Some(-24))
      }
      "return AC12 - AC14 value as AC16 if all have values" in new GrossProfitAndLossCalculator {
        calculateAC16(AC12(Some(12)), AC24(Some(2)), AC401(Some(20)), AC403(Some(9)),  AC14(Some(14))) shouldBe AC16(Some(11))
      }
    }

    "calculate AC17 and" when {
      "return empty AC17 if all are empty" in new GrossProfitAndLossCalculator {
        calculateAC17(AC13(None), AC25(None), AC402(None), AC404(None),  AC15(None)) shouldBe AC17(None)
      }
      "return AC12 value as AC17 if AC12 and AC402 has value and AC15 and AC404 are empty" in new GrossProfitAndLossCalculator {
        calculateAC17(AC13(Some(13)), AC25(None), AC402(Some(8)), AC404(None),  AC15(None)) shouldBe AC17(Some(21))
      }
      "return AC14 value as AC17 if AC12 and AC402 are empty and AC15 and AC404 has a value" in new GrossProfitAndLossCalculator {
        calculateAC17(AC13(None), AC25(None), AC402(None), AC404(Some(1)), AC15(Some(15))) shouldBe AC17(Some(-16))
      }
      "return AC12 - AC14 value as all have values" in new GrossProfitAndLossCalculator {
        calculateAC17(AC13(Some(13)), AC25(Some(2)), AC402(Some(7)), AC404(Some(4)),  AC15(Some(15))) shouldBe AC17(Some(3))
      }
    }
  }
}
