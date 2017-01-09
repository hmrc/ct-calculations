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

package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._

class AdjustedTradingProfitOrLossCalculatorSpec extends WordSpec with Matchers {

  "Calculator for Adjusted Trading Profit (CP117)" should {
    "return a trading profit calculated as CP44 + CP54 - CP59 - CP186 + CP91 + CP670 - CP668" in new AdjustedTradingProfitOrLossCalculator {
      val cp117 = calculateAdjustedTradingProfit(
        cp44 = CP44(5000),
        cp54 = CP54(1000),
        cp59 = CP59(250),
        cp186 = CP186(Some(500)),
        cp91 = CP91(Some(1000)),
        cp670 = CP670(Some(5000)),
        cp668 = CP668(Some(300))
      )
      cp117 shouldBe CP117(10950)
    }
    "return a trading profit of zero if there is a loss CP44 + CP54 - CP59 - CP186 + CP91 + CP670 - CP668" in new AdjustedTradingProfitOrLossCalculator {
      val cp117 = calculateAdjustedTradingProfit(
        cp44 = CP44(-40000),
        cp54 = CP54(1000),
        cp59 = CP59(250),
        cp186 = CP186(Some(500)),
        cp91 = CP91(Some(1000)),
        cp670 = CP670(Some(7000)),
        cp668 = CP668(Some(3000))
      )
      cp117 shouldBe CP117(0)
    }
  }

  "Calculator for Adjusted Trading loss (CP118)" should {
    "return a trading loss of zero if there is a trading profit > 0 calculated as CP44 + CP54 - CP59 - CP186 + CP91 + CP670 - CP668" in new AdjustedTradingProfitOrLossCalculator {
      val cp118 = calculateAdjustedTradingLoss(cp44 = CP44(5000),
                                              cp54 = CP54(1000),
                                              cp59 = CP59(250),
                                              cp186 = CP186(Some(500)),
                                              cp91 = CP91(Some(1000)),
                                              cp670 = CP670(Some(10000)),
                                              cp668 = CP668(Some(500)))
      cp118 shouldBe CP118(0)
    }
    "return a trading loss if there is a loss CP44 + CP54 - CP59 - CP186 + CP91 + CP670 - CP668" in new AdjustedTradingProfitOrLossCalculator {
      val cp118 = calculateAdjustedTradingLoss(cp44 = CP44(-40000),
                                              cp54 = CP54(1000),
                                              cp59 = CP59(250),
                                              cp186 = CP186(Some(500)),
                                              cp91 = CP91(Some(1000)),
                                              cp670 = CP670(Some(7000)),
                                              cp668 = CP668(Some(3000)))
      cp118 shouldBe CP118(34750)
    }
  }
}
