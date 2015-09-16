package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP293, CP294, CP295, CP999}

class NetProfitsChargeableToCtCalculatorSpec extends WordSpec with Matchers {

  "NetProfitsChargeableToCtCalculator" should {

    "return correct value when all inputs are populated" in new NetProfitsChargeableToCtCalculator {
      val result = calculateNetProfitsChargeableToCt(totalProfitsBeforeDeductions = CP293(100),
        tradingLossesOfThisPeriodAndLaterPeriods = CP294(20), totalDonations = CP999(10))

      result shouldBe CP295(70)
    }
  }
}
