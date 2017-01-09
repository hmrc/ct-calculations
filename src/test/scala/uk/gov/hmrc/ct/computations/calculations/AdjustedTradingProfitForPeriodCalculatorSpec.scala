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
import uk.gov.hmrc.ct.computations.{CP117, CP282, CPQ17}

class AdjustedTradingProfitForPeriodCalculatorSpec extends WordSpec with Matchers {

  "AdjustedTradingProfitForPeriodCalculator" should {
    "Return None if CPQ17  Trading losses not used from previous accounting periods is not defined" in new AdjustedTradingProfitForPeriodCalculator {
      val result = adjustedTradingProfitForPeriodCalculation(cp117 = CP117(100), cpq17 = CPQ17(None))
      result shouldBe CP282(None)
    }
    "Return None if CPQ17  Trading losses not used from previous accounting periods is false" in new AdjustedTradingProfitForPeriodCalculator {
      val result = adjustedTradingProfitForPeriodCalculation(cp117 = CP117(100), cpq17 = CPQ17(Some(false)))
      result shouldBe CP282(None)
    }
    "Return Some of profit or loss result if CPQ17 Trading losses not used from previous accounting periods is true" in new AdjustedTradingProfitForPeriodCalculator {
      val result = adjustedTradingProfitForPeriodCalculation(cp117 = CP117(100), cpq17 = CPQ17(Some(true)))
      result shouldBe CP282(Some(100))
    }
  }
}
