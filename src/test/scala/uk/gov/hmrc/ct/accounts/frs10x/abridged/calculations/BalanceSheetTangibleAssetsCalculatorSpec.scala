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
      "return zero if all inputs are empty or 0" in new BalanceSheetTangibleAssetsCalculator {
        calculateTangibleAssetsAtTheEndOFThePeriod(AC5217(None), AC125(None), AC126(None), AC212(None), AC213(None)) shouldBe AC217(0)
        calculateTangibleAssetsAtTheEndOFThePeriod(AC5217(Some(0)), AC125(Some(0)), AC126(Some(0)), AC212(Some(0)), AC213(Some(0))) shouldBe AC217(0)
      }

      "return zero if net value is 0" in new BalanceSheetTangibleAssetsCalculator {
        calculateTangibleAssetsAtTheEndOFThePeriod(AC5217(Some(10)), AC125(Some(9)), AC126(Some(50)), AC212(Some(1)), AC213(Some(30))) shouldBe AC217(0)
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateTangibleAssetsAtTheEndOFThePeriod(AC5217(Some(10)), AC125(Some(9)), AC126(Some(39)), AC212(Some(1)), AC213(Some(30))) shouldBe AC217(11)
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateTangibleAssetsAtTheEndOFThePeriod(AC5217(Some(10)), AC125(Some(9)), AC126(Some(8)), AC212(Some(-1)), AC213(Some(-21))) shouldBe AC217(-11)
      }
    }

    "calculating AC131" when {
      "return zero if all inputs are empty or 0" in new BalanceSheetTangibleAssetsCalculator {
        calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(AC5131(None), AC219(None), AC130(None), AC214(None)) shouldBe AC131(0)
        calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(AC5131(Some(0)), AC219(Some(0)), AC130(Some(0)), AC214(Some(0))) shouldBe AC131(0)
      }

      "return zero if net value is 0" in new BalanceSheetTangibleAssetsCalculator {
        calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(AC5131(Some(9)), AC219(Some(11)), AC130(Some(50)), AC214(Some(30))) shouldBe AC131(0)
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(AC5131(Some(9)), AC219(Some(11)), AC130(Some(61)), AC214(Some(52))) shouldBe AC131(11)
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateDepreciationOfTangibleAssetsAtEndOfThePeriod(AC5131(Some(9)), AC219(Some(11)), AC130(Some(10)), AC214(Some(-21))) shouldBe AC131(-11)
      }
    }

    "calculating AC5132" when {
      "return zero if all inputs are empty or 0" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC5217(None), AC5131(None)) shouldBe AC5132(0)
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC5217(Some(0)), AC5131(Some(0))) shouldBe AC5132(0)
      }

      "return zero if net value is 0" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC5217(Some(11)), AC5131(Some(11))) shouldBe AC5132(0)
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC5217(Some(30)), AC5131(Some(19))) shouldBe AC5132(11)
     }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC5217(Some(-14)), AC5131(Some(-3))) shouldBe AC5132(-11)
      }
    }

    "calculating AC132" when {
      "return zero if all inputs 0" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(AC217(0), AC131(0)) shouldBe AC132(0)
        }

      "return zero if net value is 0" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(AC217(11), AC131(11)) shouldBe AC132(0)
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(AC217(30), AC131(19)) shouldBe AC132(11)
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfPreviousPeriod(AC217(-14), AC131(-3)) shouldBe AC132(-11)
      }
    }
  }
}
