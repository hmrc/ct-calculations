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
import uk.gov.hmrc.ct.computations.{CP117, CP256, CP289}

class AdjustedTradingProfitCalculatorSpec extends WordSpec with Matchers {

  "AdjustedTradingProfitCalculator - optional result - CP289" should {

    "return AdjustedTradingProfitOrLossResult is it is greater equal to zero" in new AdjustedTradingProfitCalculator {
      adjustedTradingProfitCalculation(CP117(0)) shouldBe CP289(Some(0))
    }

    "return AdjustedTradingProfitOrLossResult is it is greater than zero" in new AdjustedTradingProfitCalculator {
      adjustedTradingProfitCalculation(cp117 = CP117(1000)) shouldBe CP289(Some(1000))
    }
  }

  "AdjustedTradingProfitCalculator - non-optional result - CP256" should {

    "return AdjustedTradingProfitOrLossResult as zero when CP117 equals zero" in new AdjustedTradingProfitCalculator {
      adjustedTradingProfitCalculationNonOptional(CP117(0)) shouldBe CP256(0)
    }

    "return AdjustedTradingProfitOrLossResult value when CP117 is greater than zero" in new AdjustedTradingProfitCalculator {
      adjustedTradingProfitCalculationNonOptional(CP117(1000)) shouldBe CP256(1000)
    }
  }
}
