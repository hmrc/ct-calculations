package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO13
import uk.gov.hmrc.ct.computations.{CP293, CP294}

class NetProfitsChargeableToCtWithoutDonationsCalculatorSpec extends WordSpec with Matchers {

  "NetProfitsChargeableToCtCalculator" should {
    "return CP293 - CP294 when CP293 > CP294" in new NetProfitsChargeableToCtWithoutDonationsCalculator {
      val result = calculateNetProfitsChargeableToCtWithoutDonations(CP293(100), CP294(20))
      result shouldBe CATO13(80)
    }
    "return 0 when CP293 = CP294" in new NetProfitsChargeableToCtWithoutDonationsCalculator {
      val result = calculateNetProfitsChargeableToCtWithoutDonations(CP293(100), CP294(100))
      result shouldBe CATO13(0)
    }
    "return 0 when CP293 < CP294" in new NetProfitsChargeableToCtWithoutDonationsCalculator {
      val result = calculateNetProfitsChargeableToCtWithoutDonations(CP293(100), CP294(200))
      result shouldBe CATO13(0)
    }
  }
}
