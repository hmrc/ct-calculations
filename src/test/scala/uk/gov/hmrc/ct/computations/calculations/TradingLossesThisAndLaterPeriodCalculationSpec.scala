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
import uk.gov.hmrc.ct.computations.{CP294, CP998, CP286}

class TradingLossesThisAndLaterPeriodCalculationSpec extends WordSpec with Matchers {

  "Trading Losses This And Later Period Calculation" should {
    "return CP294(0) if CP998 and CP286 contain None values" in new TradingLossesThisAndLaterPeriodCalculation {
      tradingLosses(CP286(None), CP998(None)) shouldBe CP294(0)
    }
    "return CP294(0) if CP998 and CP286 contain zero values" in new TradingLossesThisAndLaterPeriodCalculation {
      tradingLosses(CP286(Some(0)), CP998(Some(0))) shouldBe CP294(0)
    }
    "return CP294(0) if CP998 has None and CP286 contain zero value" in new TradingLossesThisAndLaterPeriodCalculation {
      tradingLosses(CP286(None), CP998(Some(0))) shouldBe CP294(0)
    }
    "return CP294(0) if CP998 has zero and CP286 contain None value" in new TradingLossesThisAndLaterPeriodCalculation {
      tradingLosses(CP286(Some(0)), CP998(None)) shouldBe CP294(0)
    }
    "return CP294(100) if CP998 has None and CP286 contain 100 value" in new TradingLossesThisAndLaterPeriodCalculation {
      tradingLosses(CP286(None), CP998(Some(100))) shouldBe CP294(100)
    }
    "return CP294(100) if CP998 has 100 and CP286 contain None value" in new TradingLossesThisAndLaterPeriodCalculation {
      tradingLosses(CP286(Some(100)), CP998(None)) shouldBe CP294(100)
    }
    "return CP294with sum of values if CP998 has a value and CP286 contains a value " in new TradingLossesThisAndLaterPeriodCalculation {
      tradingLosses(CP286(Some(286)), CP998(Some(998))) shouldBe CP294(1284)
    }
  }
}
