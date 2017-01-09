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
import uk.gov.hmrc.ct.accounts.frs102.boxes._

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

    "calculating AC131 (abridged)" when {
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

    "calculating AC133 (abridged)" when {
      "return None only if all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC124(None), AC128(None)) shouldBe AC133(None)
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC124(Some(0)), AC128(None)) shouldBe AC133(Some(0))
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC124(Some(0)), AC128(Some(0))) shouldBe AC133(Some(0))
      }

      "return zero if net value is 0" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC124(Some(11)), AC128(Some(11))) shouldBe AC133(Some(0))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC124(Some(30)), AC128(Some(19))) shouldBe AC133(Some(11))
     }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateNetBookValueOfTangibleAssetsAEndOfThePeriod(AC124(Some(-14)), AC128(Some(-3))) shouldBe AC133(Some(-11))
      }
    }

    "calculating AC132 (abridged)" when {
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

    "calculating AC124" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC124(AC124A(None), AC124B(None), AC124C(None), AC124D(None), AC124E(None)) shouldBe AC124(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC124(AC124A(Some(1)), AC124B(None), AC124C(None), AC124D(None), AC124E(None)) shouldBe AC124(Some(1))
        calculateAC124(AC124A(None), AC124B(Some(1)), AC124C(None), AC124D(None), AC124E(None)) shouldBe AC124(Some(1))
        calculateAC124(AC124A(None), AC124B(None), AC124C(Some(1)), AC124D(None), AC124E(None)) shouldBe AC124(Some(1))
        calculateAC124(AC124A(None), AC124B(None), AC124C(None), AC124D(Some(1)), AC124E(None)) shouldBe AC124(Some(1))
        calculateAC124(AC124A(None), AC124B(None), AC124C(None), AC124D(None), AC124E(Some(1))) shouldBe AC124(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC124(AC124A(Some(1)), AC124B(Some(1)), AC124C(Some(1)), AC124D(Some(1)), AC124E(Some(1))) shouldBe AC124(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC124(AC124A(Some(-1)), AC124B(Some(-1)), AC124C(Some(-1)), AC124D(Some(-1)), AC124E(Some(-1))) shouldBe AC124(Some(-5))
      }
    }

    "calculating AC125" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC125(AC125A(None), AC125B(None), AC125C(None), AC125D(None), AC125E(None)) shouldBe AC125(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC125(AC125A(Some(1)), AC125B(None), AC125C(None), AC125D(None), AC125E(None)) shouldBe AC125(Some(1))
        calculateAC125(AC125A(None), AC125B(Some(1)), AC125C(None), AC125D(None), AC125E(None)) shouldBe AC125(Some(1))
        calculateAC125(AC125A(None), AC125B(None), AC125C(Some(1)), AC125D(None), AC125E(None)) shouldBe AC125(Some(1))
        calculateAC125(AC125A(None), AC125B(None), AC125C(None), AC125D(Some(1)), AC125E(None)) shouldBe AC125(Some(1))
        calculateAC125(AC125A(None), AC125B(None), AC125C(None), AC125D(None), AC125E(Some(1))) shouldBe AC125(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC125(AC125A(Some(1)), AC125B(Some(1)), AC125C(Some(1)), AC125D(Some(1)), AC125E(Some(1))) shouldBe AC125(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC125(AC125A(Some(-1)), AC125B(Some(-1)), AC125C(Some(-1)), AC125D(Some(-1)), AC125E(Some(-1))) shouldBe AC125(Some(-5))
      }
    }

    "calculating AC126" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC126(AC126A(None), AC126B(None), AC126C(None), AC126D(None), AC126E(None)) shouldBe AC126(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC126(AC126A(Some(1)), AC126B(None), AC126C(None), AC126D(None), AC126E(None)) shouldBe AC126(Some(1))
        calculateAC126(AC126A(None), AC126B(Some(1)), AC126C(None), AC126D(None), AC126E(None)) shouldBe AC126(Some(1))
        calculateAC126(AC126A(None), AC126B(None), AC126C(Some(1)), AC126D(None), AC126E(None)) shouldBe AC126(Some(1))
        calculateAC126(AC126A(None), AC126B(None), AC126C(None), AC126D(Some(1)), AC126E(None)) shouldBe AC126(Some(1))
        calculateAC126(AC126A(None), AC126B(None), AC126C(None), AC126D(None), AC126E(Some(1))) shouldBe AC126(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC126(AC126A(Some(1)), AC126B(Some(1)), AC126C(Some(1)), AC126D(Some(1)), AC126E(Some(1))) shouldBe AC126(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC126(AC126A(Some(-1)), AC126B(Some(-1)), AC126C(Some(-1)), AC126D(Some(-1)), AC126E(Some(-1))) shouldBe AC126(Some(-5))
      }
    }

    "calculating AC127" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127(AC127A(None), AC127B(None), AC127C(None), AC127D(None), AC127E(None)) shouldBe AC127(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127(AC127A(Some(1)), AC127B(None), AC127C(None), AC127D(None), AC127E(None)) shouldBe AC127(Some(1))
        calculateAC127(AC127A(None), AC127B(Some(1)), AC127C(None), AC127D(None), AC127E(None)) shouldBe AC127(Some(1))
        calculateAC127(AC127A(None), AC127B(None), AC127C(Some(1)), AC127D(None), AC127E(None)) shouldBe AC127(Some(1))
        calculateAC127(AC127A(None), AC127B(None), AC127C(None), AC127D(Some(1)), AC127E(None)) shouldBe AC127(Some(1))
        calculateAC127(AC127A(None), AC127B(None), AC127C(None), AC127D(None), AC127E(Some(1))) shouldBe AC127(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127(AC127A(Some(1)), AC127B(Some(1)), AC127C(Some(1)), AC127D(Some(1)), AC127E(Some(1))) shouldBe AC127(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127(AC127A(Some(-1)), AC127B(Some(-1)), AC127C(Some(-1)), AC127D(Some(-1)), AC127E(Some(-1))) shouldBe AC127(Some(-5))
      }
    }

    "calculating AC127A" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127A(AC124A(None), AC125A(None), AC126A(None), AC212A(None), AC213A(None)) shouldBe AC127A(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127A(AC124A(Some(1)), AC125A(None), AC126A(None), AC212A(None), AC213A(None)) shouldBe AC127A(Some(1))
        calculateAC127A(AC124A(None), AC125A(Some(1)), AC126A(None), AC212A(None), AC213A(None)) shouldBe AC127A(Some(1))
        calculateAC127A(AC124A(None), AC125A(None), AC126A(Some(1)), AC212A(None), AC213A(None)) shouldBe AC127A(Some(-1))
        calculateAC127A(AC124A(None), AC125A(None), AC126A(None), AC212A(Some(1)), AC213A(None)) shouldBe AC127A(Some(1))
        calculateAC127A(AC124A(None), AC125A(None), AC126A(None), AC212A(None), AC213A(Some(1))) shouldBe AC127A(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127A(AC124A(Some(2)), AC125A(Some(2)), AC126A(Some(1)), AC212A(Some(1)), AC213A(Some(1))) shouldBe AC127A(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127A(AC124A(Some(1)), AC125A(Some(1)), AC126A(Some(1)), AC212A(Some(-1)), AC213A(Some(-1))) shouldBe AC127A(Some(-1))
      }
    }

    "calculating AC127B" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127B(AC124B(None), AC125B(None), AC126B(None), AC212B(None), AC213B(None)) shouldBe AC127B(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127B(AC124B(Some(1)), AC125B(None), AC126B(None), AC212B(None), AC213B(None)) shouldBe AC127B(Some(1))
        calculateAC127B(AC124B(None), AC125B(Some(1)), AC126B(None), AC212B(None), AC213B(None)) shouldBe AC127B(Some(1))
        calculateAC127B(AC124B(None), AC125B(None), AC126B(Some(1)), AC212B(None), AC213B(None)) shouldBe AC127B(Some(-1))
        calculateAC127B(AC124B(None), AC125B(None), AC126B(None), AC212B(Some(1)), AC213B(None)) shouldBe AC127B(Some(1))
        calculateAC127B(AC124B(None), AC125B(None), AC126B(None), AC212B(None), AC213B(Some(1))) shouldBe AC127B(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127B(AC124B(Some(2)), AC125B(Some(2)), AC126B(Some(1)), AC212B(Some(1)), AC213B(Some(1))) shouldBe AC127B(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127B(AC124B(Some(1)), AC125B(Some(1)), AC126B(Some(1)), AC212B(Some(-1)), AC213B(Some(-1))) shouldBe AC127B(Some(-1))
      }
    }

    "calculating AC127C" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127C(AC124C(None), AC125C(None), AC126C(None), AC212C(None), AC213C(None)) shouldBe AC127C(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127C(AC124C(Some(1)), AC125C(None), AC126C(None), AC212C(None), AC213C(None)) shouldBe AC127C(Some(1))
        calculateAC127C(AC124C(None), AC125C(Some(1)), AC126C(None), AC212C(None), AC213C(None)) shouldBe AC127C(Some(1))
        calculateAC127C(AC124C(None), AC125C(None), AC126C(Some(1)), AC212C(None), AC213C(None)) shouldBe AC127C(Some(-1))
        calculateAC127C(AC124C(None), AC125C(None), AC126C(None), AC212C(Some(1)), AC213C(None)) shouldBe AC127C(Some(1))
        calculateAC127C(AC124C(None), AC125C(None), AC126C(None), AC212C(None), AC213C(Some(1))) shouldBe AC127C(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127C(AC124C(Some(2)), AC125C(Some(2)), AC126C(Some(1)), AC212C(Some(1)), AC213C(Some(1))) shouldBe AC127C(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127C(AC124C(Some(1)), AC125C(Some(1)), AC126C(Some(1)), AC212C(Some(-1)), AC213C(Some(-1))) shouldBe AC127C(Some(-1))
      }
    }

    "calculating AC127D" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127D(AC124D(None), AC125D(None), AC126D(None), AC212D(None), AC213D(None)) shouldBe AC127D(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127D(AC124D(Some(1)), AC125D(None), AC126D(None), AC212D(None), AC213D(None)) shouldBe AC127D(Some(1))
        calculateAC127D(AC124D(None), AC125D(Some(1)), AC126D(None), AC212D(None), AC213D(None)) shouldBe AC127D(Some(1))
        calculateAC127D(AC124D(None), AC125D(None), AC126D(Some(1)), AC212D(None), AC213D(None)) shouldBe AC127D(Some(-1))
        calculateAC127D(AC124D(None), AC125D(None), AC126D(None), AC212D(Some(1)), AC213D(None)) shouldBe AC127D(Some(1))
        calculateAC127D(AC124D(None), AC125D(None), AC126D(None), AC212D(None), AC213D(Some(1))) shouldBe AC127D(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127D(AC124D(Some(2)), AC125D(Some(2)), AC126D(Some(1)), AC212D(Some(1)), AC213D(Some(1))) shouldBe AC127D(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127D(AC124D(Some(1)), AC125D(Some(1)), AC126D(Some(1)), AC212D(Some(-1)), AC213D(Some(-1))) shouldBe AC127D(Some(-1))
      }
    }

    "calculating AC127E" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127E(AC124E(None), AC125E(None), AC126E(None), AC212E(None), AC213E(None)) shouldBe AC127E(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127E(AC124E(Some(1)), AC125E(None), AC126E(None), AC212E(None), AC213E(None)) shouldBe AC127E(Some(1))
        calculateAC127E(AC124E(None), AC125E(Some(1)), AC126E(None), AC212E(None), AC213E(None)) shouldBe AC127E(Some(1))
        calculateAC127E(AC124E(None), AC125E(None), AC126E(Some(1)), AC212E(None), AC213E(None)) shouldBe AC127E(Some(-1))
        calculateAC127E(AC124E(None), AC125E(None), AC126E(None), AC212E(Some(1)), AC213E(None)) shouldBe AC127E(Some(1))
        calculateAC127E(AC124E(None), AC125E(None), AC126E(None), AC212E(None), AC213E(Some(1))) shouldBe AC127E(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127E(AC124E(Some(2)), AC125E(Some(2)), AC126E(Some(1)), AC212E(Some(1)), AC213E(Some(1))) shouldBe AC127E(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC127E(AC124E(Some(1)), AC125E(Some(1)), AC126E(Some(1)), AC212E(Some(-1)), AC213E(Some(-1))) shouldBe AC127E(Some(-1))
      }
    }

    "calculating AC128" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC128(AC128A(None), AC128B(None), AC128C(None), AC128D(None), AC128E(None)) shouldBe AC128(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC128(AC128A(Some(1)), AC128B(None), AC128C(None), AC128D(None), AC128E(None)) shouldBe AC128(Some(1))
        calculateAC128(AC128A(None), AC128B(Some(1)), AC128C(None), AC128D(None), AC128E(None)) shouldBe AC128(Some(1))
        calculateAC128(AC128A(None), AC128B(None), AC128C(Some(1)), AC128D(None), AC128E(None)) shouldBe AC128(Some(1))
        calculateAC128(AC128A(None), AC128B(None), AC128C(None), AC128D(Some(1)), AC128E(None)) shouldBe AC128(Some(1))
        calculateAC128(AC128A(None), AC128B(None), AC128C(None), AC128D(None), AC128E(Some(1))) shouldBe AC128(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC128(AC128A(Some(1)), AC128B(Some(1)), AC128C(Some(1)), AC128D(Some(1)), AC128E(Some(1))) shouldBe AC128(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC128(AC128A(Some(-1)), AC128B(Some(-1)), AC128C(Some(-1)), AC128D(Some(-1)), AC128E(Some(-1))) shouldBe AC128(Some(-5))
      }
    }

    "calculating AC129" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC129(AC129A(None), AC129B(None), AC129C(None), AC129D(None), AC129E(None)) shouldBe AC129(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC129(AC129A(Some(1)), AC129B(None), AC129C(None), AC129D(None), AC129E(None)) shouldBe AC129(Some(1))
        calculateAC129(AC129A(None), AC129B(Some(1)), AC129C(None), AC129D(None), AC129E(None)) shouldBe AC129(Some(1))
        calculateAC129(AC129A(None), AC129B(None), AC129C(Some(1)), AC129D(None), AC129E(None)) shouldBe AC129(Some(1))
        calculateAC129(AC129A(None), AC129B(None), AC129C(None), AC129D(Some(1)), AC129E(None)) shouldBe AC129(Some(1))
        calculateAC129(AC129A(None), AC129B(None), AC129C(None), AC129D(None), AC129E(Some(1))) shouldBe AC129(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC129(AC129A(Some(1)), AC129B(Some(1)), AC129C(Some(1)), AC129D(Some(1)), AC129E(Some(1))) shouldBe AC129(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC129(AC129A(Some(-1)), AC129B(Some(-1)), AC129C(Some(-1)), AC129D(Some(-1)), AC129E(Some(-1))) shouldBe AC129(Some(-5))
      }
    }

    "calculating AC130" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC130(AC130A(None), AC130B(None), AC130C(None), AC130D(None), AC130E(None)) shouldBe AC130(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC130(AC130A(Some(1)), AC130B(None), AC130C(None), AC130D(None), AC130E(None)) shouldBe AC130(Some(1))
        calculateAC130(AC130A(None), AC130B(Some(1)), AC130C(None), AC130D(None), AC130E(None)) shouldBe AC130(Some(1))
        calculateAC130(AC130A(None), AC130B(None), AC130C(Some(1)), AC130D(None), AC130E(None)) shouldBe AC130(Some(1))
        calculateAC130(AC130A(None), AC130B(None), AC130C(None), AC130D(Some(1)), AC130E(None)) shouldBe AC130(Some(1))
        calculateAC130(AC130A(None), AC130B(None), AC130C(None), AC130D(None), AC130E(Some(1))) shouldBe AC130(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC130(AC130A(Some(1)), AC130B(Some(1)), AC130C(Some(1)), AC130D(Some(1)), AC130E(Some(1))) shouldBe AC130(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC130(AC130A(Some(-1)), AC130B(Some(-1)), AC130C(Some(-1)), AC130D(Some(-1)), AC130E(Some(-1))) shouldBe AC130(Some(-5))
      }
    }

    "calculating AC131 (full)" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131(AC131A(None), AC131B(None), AC131C(None), AC131D(None), AC131E(None)) shouldBe AC131(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131(AC131A(Some(1)), AC131B(None), AC131C(None), AC131D(None), AC131E(None)) shouldBe AC131(Some(1))
        calculateAC131(AC131A(None), AC131B(Some(1)), AC131C(None), AC131D(None), AC131E(None)) shouldBe AC131(Some(1))
        calculateAC131(AC131A(None), AC131B(None), AC131C(Some(1)), AC131D(None), AC131E(None)) shouldBe AC131(Some(1))
        calculateAC131(AC131A(None), AC131B(None), AC131C(None), AC131D(Some(1)), AC131E(None)) shouldBe AC131(Some(1))
        calculateAC131(AC131A(None), AC131B(None), AC131C(None), AC131D(None), AC131E(Some(1))) shouldBe AC131(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131(AC131A(Some(1)), AC131B(Some(1)), AC131C(Some(1)), AC131D(Some(1)), AC131E(Some(1))) shouldBe AC131(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131(AC131A(Some(-1)), AC131B(Some(-1)), AC131C(Some(-1)), AC131D(Some(-1)), AC131E(Some(-1))) shouldBe AC131(Some(-5))
      }
    }

    "calculating AC131A" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131A(AC128A(None), AC129A(None), AC130A(None), AC214A(None)) shouldBe AC131A(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131A(AC128A(Some(1)), AC129A(None), AC130A(None), AC214A(None)) shouldBe AC131A(Some(1))
        calculateAC131A(AC128A(None), AC129A(Some(1)), AC130A(None), AC214A(None)) shouldBe AC131A(Some(1))
        calculateAC131A(AC128A(None), AC129A(None), AC130A(Some(1)), AC214A(None)) shouldBe AC131A(Some(-1))
        calculateAC131A(AC128A(None), AC129A(None), AC130A(None), AC214A(Some(1))) shouldBe AC131A(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131A(AC128A(Some(2)), AC129A(Some(2)), AC130A(Some(1)), AC214A(Some(1))) shouldBe AC131A(Some(4))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131A(AC128A(Some(1)), AC129A(Some(1)), AC130A(Some(2)), AC214A(Some(-1))) shouldBe AC131A(Some(-1))
      }
    }

    "calculating AC131B" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131B(AC128B(None), AC129B(None), AC130B(None), AC214B(None)) shouldBe AC131B(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131B(AC128B(Some(1)), AC129B(None), AC130B(None), AC214B(None)) shouldBe AC131B(Some(1))
        calculateAC131B(AC128B(None), AC129B(Some(1)), AC130B(None), AC214B(None)) shouldBe AC131B(Some(1))
        calculateAC131B(AC128B(None), AC129B(None), AC130B(Some(1)), AC214B(None)) shouldBe AC131B(Some(-1))
        calculateAC131B(AC128B(None), AC129B(None), AC130B(None), AC214B(Some(1))) shouldBe AC131B(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131B(AC128B(Some(2)), AC129B(Some(2)), AC130B(Some(1)), AC214B(Some(1))) shouldBe AC131B(Some(4))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131B(AC128B(Some(1)), AC129B(Some(1)), AC130B(Some(2)), AC214B(Some(-1))) shouldBe AC131B(Some(-1))
      }
    }

    "calculating AC131C" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131C(AC128C(None), AC129C(None), AC130C(None), AC214C(None)) shouldBe AC131C(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131C(AC128C(Some(1)), AC129C(None), AC130C(None), AC214C(None)) shouldBe AC131C(Some(1))
        calculateAC131C(AC128C(None), AC129C(Some(1)), AC130C(None), AC214C(None)) shouldBe AC131C(Some(1))
        calculateAC131C(AC128C(None), AC129C(None), AC130C(Some(1)), AC214C(None)) shouldBe AC131C(Some(-1))
        calculateAC131C(AC128C(None), AC129C(None), AC130C(None), AC214C(Some(1))) shouldBe AC131C(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131C(AC128C(Some(2)), AC129C(Some(2)), AC130C(Some(1)), AC214C(Some(1))) shouldBe AC131C(Some(4))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131C(AC128C(Some(1)), AC129C(Some(1)), AC130C(Some(2)), AC214C(Some(-1))) shouldBe AC131C(Some(-1))
      }
    }

    "calculating AC131D" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131D(AC128D(None), AC129D(None), AC130D(None), AC214D(None)) shouldBe AC131D(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131D(AC128D(Some(1)), AC129D(None), AC130D(None), AC214D(None)) shouldBe AC131D(Some(1))
        calculateAC131D(AC128D(None), AC129D(Some(1)), AC130D(None), AC214D(None)) shouldBe AC131D(Some(1))
        calculateAC131D(AC128D(None), AC129D(None), AC130D(Some(1)), AC214D(None)) shouldBe AC131D(Some(-1))
        calculateAC131D(AC128D(None), AC129D(None), AC130D(None), AC214D(Some(1))) shouldBe AC131D(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131D(AC128D(Some(2)), AC129D(Some(2)), AC130D(Some(1)), AC214D(Some(1))) shouldBe AC131D(Some(4))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131D(AC128D(Some(1)), AC129D(Some(1)), AC130D(Some(2)), AC214D(Some(-1))) shouldBe AC131D(Some(-1))
      }
    }

    "calculating AC131E" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131E(AC128E(None), AC129E(None), AC130E(None), AC214E(None)) shouldBe AC131E(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131E(AC128E(Some(1)), AC129E(None), AC130E(None), AC214E(None)) shouldBe AC131E(Some(1))
        calculateAC131E(AC128E(None), AC129E(Some(1)), AC130E(None), AC214E(None)) shouldBe AC131E(Some(1))
        calculateAC131E(AC128E(None), AC129E(None), AC130E(Some(1)), AC214E(None)) shouldBe AC131E(Some(-1))
        calculateAC131E(AC128E(None), AC129E(None), AC130E(None), AC214E(Some(1))) shouldBe AC131E(Some(1))

      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131E(AC128E(Some(2)), AC129E(Some(2)), AC130E(Some(1)), AC214E(Some(1))) shouldBe AC131E(Some(4))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC131E(AC128E(Some(1)), AC129E(Some(1)), AC130E(Some(2)), AC214E(Some(-1))) shouldBe AC131E(Some(-1))
      }
    }

    "calculating AC132 (full)" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132(AC132A(None), AC132B(None), AC132C(None), AC132D(None), AC132E(None)) shouldBe AC132(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132(AC132A(Some(1)), AC132B(None), AC132C(None), AC132D(None), AC132E(None)) shouldBe AC132(Some(1))
        calculateAC132(AC132A(None), AC132B(Some(1)), AC132C(None), AC132D(None), AC132E(None)) shouldBe AC132(Some(1))
        calculateAC132(AC132A(None), AC132B(None), AC132C(Some(1)), AC132D(None), AC132E(None)) shouldBe AC132(Some(1))
        calculateAC132(AC132A(None), AC132B(None), AC132C(None), AC132D(Some(1)), AC132E(None)) shouldBe AC132(Some(1))
        calculateAC132(AC132A(None), AC132B(None), AC132C(None), AC132D(None), AC132E(Some(1))) shouldBe AC132(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132(AC132A(Some(1)), AC132B(Some(1)), AC132C(Some(1)), AC132D(Some(1)), AC132E(Some(1))) shouldBe AC132(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132(AC132A(Some(-1)), AC132B(Some(-1)), AC132C(Some(-1)), AC132D(Some(-1)), AC132E(Some(-1))) shouldBe AC132(Some(-5))
      }
    }

    "calculating AC132A" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132A(AC127A(None), AC131A(None)) shouldBe AC132A(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132A(AC127A(Some(1)), AC131A(None)) shouldBe AC132A(Some(1))
        calculateAC132A(AC127A(None), AC131A(Some(1))) shouldBe AC132A(Some(-1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132A(AC127A(Some(2)), AC131A(Some(1))) shouldBe AC132A(Some(1))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132A(AC127A(Some(1)), AC131A(Some(2))) shouldBe AC132A(Some(-1))
      }
    }

    "calculating AC132B" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132B(AC127B(None), AC131B(None)) shouldBe AC132B(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132B(AC127B(Some(1)), AC131B(None)) shouldBe AC132B(Some(1))
        calculateAC132B(AC127B(None), AC131B(Some(1))) shouldBe AC132B(Some(-1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132B(AC127B(Some(2)), AC131B(Some(1))) shouldBe AC132B(Some(1))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132B(AC127B(Some(1)), AC131B(Some(2))) shouldBe AC132B(Some(-1))
      }
    }

    "calculating AC132C" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132C(AC127C(None), AC131C(None)) shouldBe AC132C(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132C(AC127C(Some(1)), AC131C(None)) shouldBe AC132C(Some(1))
        calculateAC132C(AC127C(None), AC131C(Some(1))) shouldBe AC132C(Some(-1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132C(AC127C(Some(2)), AC131C(Some(1))) shouldBe AC132C(Some(1))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132C(AC127C(Some(1)), AC131C(Some(2))) shouldBe AC132C(Some(-1))
      }
    }

    "calculating AC132D" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132D(AC127D(None), AC131D(None)) shouldBe AC132D(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132D(AC127D(Some(1)), AC131D(None)) shouldBe AC132D(Some(1))
        calculateAC132D(AC127D(None), AC131D(Some(1))) shouldBe AC132D(Some(-1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132D(AC127D(Some(2)), AC131D(Some(1))) shouldBe AC132D(Some(1))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132D(AC127D(Some(1)), AC131D(Some(2))) shouldBe AC132D(Some(-1))
      }
    }

    "calculating AC132E" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132E(AC127E(None), AC131E(None)) shouldBe AC132E(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132E(AC127E(Some(1)), AC131E(None)) shouldBe AC132E(Some(1))
        calculateAC132E(AC127E(None), AC131E(Some(1))) shouldBe AC132E(Some(-1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132E(AC127E(Some(2)), AC131E(Some(1))) shouldBe AC132E(Some(1))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC132E(AC127E(Some(1)), AC131E(Some(2))) shouldBe AC132E(Some(-1))
      }
    }

    "calculating AC133 (full)" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133(AC133A(None), AC133B(None), AC133C(None), AC133D(None), AC133E(None)) shouldBe AC133(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133(AC133A(Some(1)), AC133B(None), AC133C(None), AC133D(None), AC133E(None)) shouldBe AC133(Some(1))
        calculateAC133(AC133A(None), AC133B(Some(1)), AC133C(None), AC133D(None), AC133E(None)) shouldBe AC133(Some(1))
        calculateAC133(AC133A(None), AC133B(None), AC133C(Some(1)), AC133D(None), AC133E(None)) shouldBe AC133(Some(1))
        calculateAC133(AC133A(None), AC133B(None), AC133C(None), AC133D(Some(1)), AC133E(None)) shouldBe AC133(Some(1))
        calculateAC133(AC133A(None), AC133B(None), AC133C(None), AC133D(None), AC133E(Some(1))) shouldBe AC133(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133(AC133A(Some(1)), AC133B(Some(1)), AC133C(Some(1)), AC133D(Some(1)), AC133E(Some(1))) shouldBe AC133(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133(AC133A(Some(-1)), AC133B(Some(-1)), AC133C(Some(-1)), AC133D(Some(-1)), AC133E(Some(-1))) shouldBe AC133(Some(-5))
      }
    }

    "calculating AC133A" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133A(AC124A(None), AC128A(None)) shouldBe AC133A(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133A(AC124A(Some(1)), AC128A(None)) shouldBe AC133A(Some(1))
        calculateAC133A(AC124A(None), AC128A(Some(1))) shouldBe AC133A(Some(-1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133A(AC124A(Some(2)), AC128A(Some(1))) shouldBe AC133A(Some(1))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133A(AC124A(Some(1)), AC128A(Some(2))) shouldBe AC133A(Some(-1))
      }
    }

    "calculating AC133B" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133B(AC124B(None), AC128B(None)) shouldBe AC133B(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133B(AC124B(Some(1)), AC128B(None)) shouldBe AC133B(Some(1))
        calculateAC133B(AC124B(None), AC128B(Some(1))) shouldBe AC133B(Some(-1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133B(AC124B(Some(2)), AC128B(Some(1))) shouldBe AC133B(Some(1))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133B(AC124B(Some(1)), AC128B(Some(2))) shouldBe AC133B(Some(-1))
      }
    }

    "calculating AC133C" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133C(AC124C(None), AC128C(None)) shouldBe AC133C(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133C(AC124C(Some(1)), AC128C(None)) shouldBe AC133C(Some(1))
        calculateAC133C(AC124C(None), AC128C(Some(1))) shouldBe AC133C(Some(-1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133C(AC124C(Some(2)), AC128C(Some(1))) shouldBe AC133C(Some(1))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133C(AC124C(Some(1)), AC128C(Some(2))) shouldBe AC133C(Some(-1))
      }
    }

    "calculating AC133D" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133D(AC124D(None), AC128D(None)) shouldBe AC133D(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133D(AC124D(Some(1)), AC128D(None)) shouldBe AC133D(Some(1))
        calculateAC133D(AC124D(None), AC128D(Some(1))) shouldBe AC133D(Some(-1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133D(AC124D(Some(2)), AC128D(Some(1))) shouldBe AC133D(Some(1))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133D(AC124D(Some(1)), AC128D(Some(2))) shouldBe AC133D(Some(-1))
      }
    }

    "calculating AC133E" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133E(AC124E(None), AC128E(None)) shouldBe AC133E(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133E(AC124E(Some(1)), AC128E(None)) shouldBe AC133E(Some(1))
        calculateAC133E(AC124E(None), AC128E(Some(1))) shouldBe AC133E(Some(-1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133E(AC124E(Some(2)), AC128E(Some(1))) shouldBe AC133E(Some(1))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC133E(AC124E(Some(1)), AC128E(Some(2))) shouldBe AC133E(Some(-1))
      }
    }

    "calculating AC212" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC212(AC212A(None), AC212B(None), AC212C(None), AC212D(None), AC212E(None)) shouldBe AC212(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC212(AC212A(Some(1)), AC212B(None), AC212C(None), AC212D(None), AC212E(None)) shouldBe AC212(Some(1))
        calculateAC212(AC212A(None), AC212B(Some(1)), AC212C(None), AC212D(None), AC212E(None)) shouldBe AC212(Some(1))
        calculateAC212(AC212A(None), AC212B(None), AC212C(Some(1)), AC212D(None), AC212E(None)) shouldBe AC212(Some(1))
        calculateAC212(AC212A(None), AC212B(None), AC212C(None), AC212D(Some(1)), AC212E(None)) shouldBe AC212(Some(1))
        calculateAC212(AC212A(None), AC212B(None), AC212C(None), AC212D(None), AC212E(Some(1))) shouldBe AC212(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC212(AC212A(Some(1)), AC212B(Some(1)), AC212C(Some(1)), AC212D(Some(1)), AC212E(Some(1))) shouldBe AC212(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC212(AC212A(Some(-1)), AC212B(Some(-1)), AC212C(Some(-1)), AC212D(Some(-1)), AC212E(Some(-1))) shouldBe AC212(Some(-5))
      }
    }

    "calculating AC213" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC213(AC213A(None), AC213B(None), AC213C(None), AC213D(None), AC213E(None)) shouldBe AC213(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC213(AC213A(Some(1)), AC213B(None), AC213C(None), AC213D(None), AC213E(None)) shouldBe AC213(Some(1))
        calculateAC213(AC213A(None), AC213B(Some(1)), AC213C(None), AC213D(None), AC213E(None)) shouldBe AC213(Some(1))
        calculateAC213(AC213A(None), AC213B(None), AC213C(Some(1)), AC213D(None), AC213E(None)) shouldBe AC213(Some(1))
        calculateAC213(AC213A(None), AC213B(None), AC213C(None), AC213D(Some(1)), AC213E(None)) shouldBe AC213(Some(1))
        calculateAC213(AC213A(None), AC213B(None), AC213C(None), AC213D(None), AC213E(Some(1))) shouldBe AC213(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC213(AC213A(Some(1)), AC213B(Some(1)), AC213C(Some(1)), AC213D(Some(1)), AC213E(Some(1))) shouldBe AC213(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC213(AC213A(Some(-1)), AC213B(Some(-1)), AC213C(Some(-1)), AC213D(Some(-1)), AC213E(Some(-1))) shouldBe AC213(Some(-5))
      }
    }

    "calculating AC214" when {
      "return None when all inputs are empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC214(AC214A(None), AC214B(None), AC214C(None), AC214D(None), AC214E(None)) shouldBe AC214(None)
      }

      "return correct value if at least one input is non empty" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC214(AC214A(Some(1)), AC214B(None), AC214C(None), AC214D(None), AC214E(None)) shouldBe AC214(Some(1))
        calculateAC214(AC214A(None), AC214B(Some(1)), AC214C(None), AC214D(None), AC214E(None)) shouldBe AC214(Some(1))
        calculateAC214(AC214A(None), AC214B(None), AC214C(Some(1)), AC214D(None), AC214E(None)) shouldBe AC214(Some(1))
        calculateAC214(AC214A(None), AC214B(None), AC214C(None), AC214D(Some(1)), AC214E(None)) shouldBe AC214(Some(1))
        calculateAC214(AC214A(None), AC214B(None), AC214C(None), AC214D(None), AC214E(Some(1))) shouldBe AC214(Some(1))
      }

      "return correct positive value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC214(AC214A(Some(1)), AC214B(Some(1)), AC214C(Some(1)), AC214D(Some(1)), AC214E(Some(1))) shouldBe AC214(Some(5))
      }

      "return correct negative value" in new BalanceSheetTangibleAssetsCalculator {
        calculateAC214(AC214A(Some(-1)), AC214B(Some(-1)), AC214C(Some(-1)), AC214D(Some(-1)), AC214E(Some(-1))) shouldBe AC214(Some(-5))
      }
    }
  }
}
