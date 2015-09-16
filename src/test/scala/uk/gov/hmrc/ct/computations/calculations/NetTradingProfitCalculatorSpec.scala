package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP117, CP283, CP284, CP291}

class NetTradingProfitCalculatorSpec extends WordSpec with Matchers {

  "NetTradingProfitCalculator" should {

    "return correct value" in new NetTradingProfitCalculator {
      val result = netTradingProfitCalculation(cp117 = CP117(1000),
        cp283 = CP283(Some(800)))
      result shouldBe CP284(Some(200))
    }

    "return correct value when losses has no value" in new NetTradingProfitCalculator {
      val result = netTradingProfitCalculation(cp117 = CP117(1000),
        cp283 = CP283(None))
      result shouldBe CP284(Some(1000))
    }
  }

  "NetTradingProfitCalculator for profits chargeable" should {

    "return value of CP284 if CP283 > 0" in new NetTradingProfitCalculator {
      val result = netTradingProfitForProfitsChargeable(netTradingProfit = CP284(Some(1000)),
        lossesBroughtForwardUsedAgainstTradingProfit = CP283(Some(500)))
      result shouldBe CP291(Some(1000))
    }

    "return None if CP283 = 0" in new NetTradingProfitCalculator {
      val result = netTradingProfitForProfitsChargeable(netTradingProfit = CP284(Some(1000)),
        lossesBroughtForwardUsedAgainstTradingProfit = CP283(Some(0)))
      result shouldBe CP291(None)
    }

    "return None if CP283 < 0" in new NetTradingProfitCalculator {
      val result = netTradingProfitForProfitsChargeable(netTradingProfit = CP284(Some(1000)),
        lossesBroughtForwardUsedAgainstTradingProfit = CP283(Some(-1)))
      result shouldBe CP291(None)
    }
  }
}
