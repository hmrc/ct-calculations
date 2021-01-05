/*
 * Copyright 2021 HM Revenue & Customs
 *
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
