package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP301, CP302, CP999, CPQ21}

class TotalDonationsCalculatorSpec extends WordSpec with Matchers {

  "TotalDonationsCalculator" should {
    "return 0 if didCompanyMakeDonations is false" in new TotalDonationsCalculator {
      val totalDonations = calculateTotalDonations(
        cpq21 = CPQ21(value = false),
        cp301 = CP301(Some(100)),
        cp302 = CP302(Some(200)))
      totalDonations shouldBe CP999(0)
    }
    "return sum values if didCompanyMakeDonations is true" in new TotalDonationsCalculator {
      val totalDonations = calculateTotalDonations(
        cpq21 = CPQ21(value = true),
        cp301 = CP301(Some(100)),
        cp302 = CP302(Some(200)))
      totalDonations shouldBe CP999(300)
    }
  }

}
