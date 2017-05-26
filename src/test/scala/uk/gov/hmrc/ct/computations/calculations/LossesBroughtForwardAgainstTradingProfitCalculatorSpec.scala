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

class LossesBroughtForwardAgainstTradingProfitCalculatorSpec extends WordSpec with Matchers {

  "LossesBroughtForwardAgainstTradingProfitCalculator" should {

    "return None if CPQ17 Trading losses not used from previous accounting periods is None" in new LossesBroughtForwardAgainstTradingProfitCalculator {
      val result = lossesBroughtForwardUsedAgainstTradingProfitCalculation(cpq17 = CPQ17(None),
        cp281 = CP281(100),
        cp282 = CP282(Some(1000)),
        cp283a = CP283a(None),
        cp283b = CP283b(None))
      result shouldBe CP283(None)
    }
    "return None if CPQ17 Trading losses not used from previous accounting periods is false" in new LossesBroughtForwardAgainstTradingProfitCalculator {
      val result = lossesBroughtForwardUsedAgainstTradingProfitCalculation(cpq17 = CPQ17(Some(false)),
                                                                                cp281 = CP281(100),
                                                                                cp282 = CP282(Some(1000)),
                                                                                cp283a = CP283a(None),
                                                                                cp283b = CP283b(None))
      result shouldBe CP283(None)
    }
    "return lesser of losses and adjusted trading profit if CPQ17 Trading losses not used from previous accounting periods is true" in new LossesBroughtForwardAgainstTradingProfitCalculator {
      val result = lossesBroughtForwardUsedAgainstTradingProfitCalculation(cpq17 = CPQ17(Some(true)),
                                                                                cp281 = CP281(100),
                                                                                cp282 = CP282(Some(1000)),
                                                                                cp283a = CP283a(None),
                                                                                cp283b = CP283b(None))
      result shouldBe CP283(Some(100))
    }
    "return adjusted trading profit if it is less than losses brought forward and if CPQ17 Trading losses not used from previous accounting periods is true" in new LossesBroughtForwardAgainstTradingProfitCalculator {
      val result = lossesBroughtForwardUsedAgainstTradingProfitCalculation(cpq17 = CPQ17(Some(true)),
                                                                                cp281 = CP281(10000),
                                                                                cp282 = CP282(Some(1000)),
                                                                                cp283a = CP283a(None),
                                                                                cp283b = CP283b(None))
      result shouldBe CP283(Some(1000))
    }
    "return None if CP117 adjusted trading profit is None and CPQ17 Trading losses not used from previous accounting periods is true" in new LossesBroughtForwardAgainstTradingProfitCalculator {
      val result = lossesBroughtForwardUsedAgainstTradingProfitCalculation(cpq17 = CPQ17(Some(true)),
                                                                                cp281 = CP281(100),
                                                                                cp282 = CP282(None),
                                                                                cp283a = CP283a(None),
                                                                                cp283b = CP283b(None))
      result shouldBe CP283(None)
    }
    "return the sum of 283a and 283b if they are provided" in  new LossesBroughtForwardAgainstTradingProfitCalculator {
      val result = lossesBroughtForwardUsedAgainstTradingProfitCalculation(cpq17 = CPQ17(Some(true)),
        cp281 = CP281(100),
        cp282 = CP282(None),
        cp283a = CP283a(Some(10)),
        cp283b = CP283b(Some(15)))

      result shouldBe CP283(Some(25))
    }
  }

  "LossesBroughtForwardAgainstTradingProfitCalculator profitsChargeable" should {

    "set the ProfitsChargeable field CP290 if the value of CP283 greater than 0" in new LossesBroughtForwardAgainstTradingProfitCalculator {
      val result = lossesBroughtForwardUsedAgainstTradingProfitForProfitsChargeable(CP283(Some(100)))
      result shouldBe CP290(Some(100))
    }
    "not set the ProfitsChargeable field CP290 if the value of CP283 is equal to 0" in new LossesBroughtForwardAgainstTradingProfitCalculator {
      val result = lossesBroughtForwardUsedAgainstTradingProfitForProfitsChargeable(CP283(Some(0)))
      result shouldBe CP290(None)
    }
    "not set the ProfitsChargeable field CP290 if the value of CP283 is less than 0" in new LossesBroughtForwardAgainstTradingProfitCalculator {
      val result = lossesBroughtForwardUsedAgainstTradingProfitForProfitsChargeable(CP283(Some(-1)))
      result shouldBe CP290(None)
    }
    "not set the ProfitsChargeable field CP290 if the value of CP283 is not set" in new LossesBroughtForwardAgainstTradingProfitCalculator {
      val result = lossesBroughtForwardUsedAgainstTradingProfitForProfitsChargeable(CP283(None))
      result shouldBe CP290(None)
    }
  }
}
