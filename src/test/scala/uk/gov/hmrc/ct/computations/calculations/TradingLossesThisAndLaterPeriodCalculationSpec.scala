/*
 * Copyright 2021 HM Revenue & Customs
 *
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
