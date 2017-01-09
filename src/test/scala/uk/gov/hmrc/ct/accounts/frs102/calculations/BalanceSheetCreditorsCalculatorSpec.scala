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

class BalanceSheetCreditorsCalculatorSpec extends WordSpec with Matchers {

  "BalanceSheetTangibleAssetsCalculator" should {
    
    "calculating AC162" when {
      "return None only if all inputs are empty" in new BalanceSheetCreditorsCalculator {
        calculateAC162(AC156(None), AC158(None), AC160(None)) shouldBe AC162(None)
      }

      "return zero if net value is 0" in new BalanceSheetCreditorsCalculator {
        calculateAC162(AC156(Some(10)), AC158(Some(-5)), AC160(Some(-5))) shouldBe AC162(Some(0))
      }

      "return correct positive value" in new BalanceSheetCreditorsCalculator {
        calculateAC162(AC156(Some(10)), AC158(Some(9)), AC160(Some(39))) shouldBe AC162(Some(58))
      }

      "return correct negative value" in new BalanceSheetCreditorsCalculator {
        calculateAC162(AC156(Some(10)), AC158(Some(-10)), AC160(Some(-10))) shouldBe AC162(Some(-10))
      }
    }

    "calculating AC163" when {
      "return None only if all inputs are empty" in new BalanceSheetCreditorsCalculator {
        calculateAC163(AC157(None), AC159(None), AC161(None)) shouldBe AC163(None)
      }

      "return zero if net value is 0" in new BalanceSheetCreditorsCalculator {
        calculateAC163(AC157(Some(10)), AC159(Some(-5)), AC161(Some(-5))) shouldBe AC163(Some(0))
      }

      "return correct positive value" in new BalanceSheetCreditorsCalculator {
        calculateAC163(AC157(Some(10)), AC159(Some(9)), AC161(Some(39))) shouldBe AC163(Some(58))
      }

      "return correct negative value" in new BalanceSheetCreditorsCalculator {
        calculateAC163(AC157(Some(10)), AC159(Some(-10)), AC161(Some(-10))) shouldBe AC163(Some(-10))
      }
    }
    
  }
}
