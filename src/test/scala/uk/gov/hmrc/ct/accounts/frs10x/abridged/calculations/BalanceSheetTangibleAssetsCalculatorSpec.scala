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

package uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.abridged._

class BalanceSheetTangibleAssetsCalculatorSpec extends WordSpec with Matchers {

  "BalanceSheetTangibleAssetsCalculator" should {
    "calculating AC217" when {
      "return None only if all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateTangibleAssetsAtTheEndOFThePeriod(AC124(None), AC125(None), AC126(None), AC212(None), AC213(None)) shouldBe AC217(None)
        calculateTangibleAssetsAtTheEndOFThePeriod(AC124(Some(0)), AC125(None), AC126(Some(0)), AC212(None), AC213(Some(0))) shouldBe AC217(Some(0))
        calculateTangibleAssetsAtTheEndOFThePeriod(AC124(Some(0)), AC125(Some(0)), AC126(Some(0)), AC212(Some(0)), AC213(Some(0))) shouldBe AC217(Some(0))
      }

      "return zero if net value is 0" in new BalanceSheetTangibleAssetsCalculator {
        calculateTangibleAssetsAtTheEndOFThePeriod(AC124(Some(10)), AC125(Some(9)), AC126(Some(50)), AC212(Some(1)), AC213(Some(30))) shouldBe AC217(Some(0))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateTangibleAssetsAtTheEndOFThePeriod(AC124(Some(10)), AC125(Some(9)), AC126(Some(39)), AC212(Some(1)), AC213(Some(30))) shouldBe AC217(Some(11))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateTangibleAssetsAtTheEndOFThePeriod(AC124(Some(10)), AC125(Some(9)), AC126(Some(8)), AC212(Some(-1)), AC213(Some(-21))) shouldBe AC217(Some(-11))
      }
    }

    "calculating AC131" when {
      "return None only if all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(AC128(None), AC219(None), AC130(None), AC214(None)) shouldBe AC131(None)
        calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(AC128(Some(0)), AC219(None), AC130(Some(0)), AC214(None)) shouldBe AC131(Some(0))
        calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(AC128(Some(0)), AC219(Some(0)), AC130(Some(0)), AC214(Some(0))) shouldBe AC131(Some(0))
      }

      "return zero if net value is 0" in new BalanceSheetTangibleAssetsCalculator {
        calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(AC128(Some(9)), AC219(Some(11)), AC130(Some(50)), AC214(Some(30))) shouldBe AC131(Some(0))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(AC128(Some(9)), AC219(Some(11)), AC130(Some(61)), AC214(Some(52))) shouldBe AC131(Some(11))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(AC128(Some(9)), AC219(Some(11)), AC130(Some(10)), AC214(Some(-21))) shouldBe AC131(Some(-11))
      }
    }

    "calculating AC5132" when {
      "return None only if all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC124(None), AC128(None)) shouldBe AC5132(None)
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC124(Some(0)), AC128(None)) shouldBe AC5132(Some(0))
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC124(Some(0)), AC128(Some(0))) shouldBe AC5132(Some(0))
      }

      "return zero if net value is 0" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC124(Some(11)), AC128(Some(11))) shouldBe AC5132(Some(0))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC124(Some(30)), AC128(Some(19))) shouldBe AC5132(Some(11))
     }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC124(Some(-14)), AC128(Some(-3))) shouldBe AC5132(Some(-11))
      }
    }

    "calculating AC132" when {
      "return None only if all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(AC217(None), AC131(None)) shouldBe AC132(None)
        calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(AC217(Some(0)), AC131(None)) shouldBe AC132(Some(0))
        calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(AC217(Some(0)), AC131(Some(0))) shouldBe AC132(Some(0))
      }

      "return zero if net value is 0" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(AC217(Some(11)), AC131(Some(11))) shouldBe AC132(Some(0))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(AC217(Some(30)), AC131(Some(19))) shouldBe AC132(Some(11))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(AC217(Some(-14)), AC131(Some(-3))) shouldBe AC132(Some(-11))
      }
    }
  }
}
