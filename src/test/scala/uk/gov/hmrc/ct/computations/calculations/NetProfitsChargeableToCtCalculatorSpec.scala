/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

class NetProfitsChargeableToCtCalculatorSpec extends WordSpec with Matchers {

  "NetProfitsChargeableToCtCalculator" should {

    "return correct value when CP997 is None are populated" in new NetProfitsChargeableToCtCalculator {
      val result = calculateNetProfitsChargeableToCt(totalProfitsBeforeDeductions = CP293(100),
        tradingLossesOfThisPeriodAndLaterPeriods = CP294(20),
        tradingLossesFromEarlierPeriodsUsedAgainstNonTradingProfit = CP997(None),
        totalDonations = CP999(10))

      result shouldBe CP295(70)
    }

    "return correct value when all inputs are populated" in new NetProfitsChargeableToCtCalculator {
      val result = calculateNetProfitsChargeableToCt(totalProfitsBeforeDeductions = CP293(100),
        tradingLossesOfThisPeriodAndLaterPeriods = CP294(20),
        tradingLossesFromEarlierPeriodsUsedAgainstNonTradingProfit = CP997(10),
        totalDonations = CP999(10))

      result shouldBe CP295(60)
    }
  }
}
