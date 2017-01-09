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
import uk.gov.hmrc.ct.accounts.frs102.boxes.{AC23, _}

class OperatingProfitOrLossCalculatorSpec extends WordSpec with Matchers {

  "OperatingProfitOrLossCalculator" should {
    "calculating AC26 for abridged accounts" when {
      "return zero if all inputs are empty" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(None), AC18(None), AC20(None)) shouldBe AC26(None)
      }

      "return zero if all inputs are zero" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(0)), AC18(Some(0)), AC20(Some(0))) shouldBe AC26(Some(0))
      }

      "return sum if all values positive" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(16)), AC18(Some(18)), AC20(Some(20))) shouldBe AC26(Some(-22))
      }

      "return sum if values positive and negative" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(16)), AC18(Some(-18)), AC20(Some(20))) shouldBe AC26(Some(14))
      }

      "return sum if all values negative" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(-16)), AC18(Some(-18)), AC20(Some(-20))) shouldBe AC26(Some(22))
      }
    }

    "calculating AC26 for full accounts" when {
      "return zero if all inputs are empty" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(None), AC18(None), AC20(None), AC22(None)) shouldBe AC26(None)
      }

      "return zero if all inputs are zero" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(0)), AC18(Some(0)), AC20(Some(0)), AC22(Some(0))) shouldBe AC26(Some(0))
      }

      "return sum if all values positive" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(16)), AC18(Some(18)), AC20(Some(20)), AC22(Some(220))) shouldBe AC26(Some(198))
      }

      "return sum if values positive and negative" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(16)), AC18(Some(-18)), AC20(Some(20)), AC22(Some(-22))) shouldBe AC26(Some(-8))
      }

      "return sum if all values negative" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(-16)), AC18(Some(-18)), AC20(Some(-20)), AC22(Some(-44))) shouldBe AC26(Some(-22))
      }
    }
  }

  "OperatingProfitOrLossCalculator" should {
    "calculating AC27 for abridged accounts" when {
      "return zero if all inputs are empty" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(None), AC19(None), AC21(None)) shouldBe AC27(None)
      }

      "return zero if all inputs are zero" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(0)), AC19(Some(0)), AC21(Some(0))) shouldBe AC27(Some(0))
      }

      "return sum if all values positive" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(16)), AC19(Some(18)), AC21(Some(20))) shouldBe AC27(Some(-22))
      }

      "return sum if values positive and negative" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(16)), AC19(Some(-18)), AC21(Some(20))) shouldBe AC27(Some(14))
      }

      "return sum if all values negative" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(-16)), AC19(Some(-18)), AC21(Some(-20))) shouldBe AC27(Some(22))
      }
    }

    "calculating AC27 for full accounts" when {
      "return zero if all inputs are empty" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(None), AC19(None), AC21(None), AC23(None)) shouldBe AC27(None)
      }

      "return zero if all inputs are zero" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(0)), AC19(Some(0)), AC21(Some(0)), AC23(Some(0))) shouldBe AC27(Some(0))
      }

      "return sum if all values positive" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(16)), AC19(Some(18)), AC21(Some(20)), AC23(Some(23))) shouldBe AC27(Some(1))
      }

      "return sum if values positive and negative" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(16)), AC19(Some(-18)), AC21(Some(20)), AC23(Some(23))) shouldBe AC27(Some(37))
      }

      "return sum if all values negative" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(-16)), AC19(Some(-18)), AC21(Some(-20)), AC23(Some(-23))) shouldBe AC27(Some(-1))
      }
    }
  }
}
