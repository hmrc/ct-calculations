package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP85, CP86, CP87}

class TotalFirstYearAllowanceClaimedCalculationSpec extends WordSpec with Matchers {

  "TotalFirstYearAllowanceClaimedCalculation" should {
    "return an option with the calculation value" in new TotalFirstYearAllowanceClaimedCalculation {
      totalFirstYearAllowanceClaimedCalculation(cp85 = CP85(Some(1)), cp86 = CP86(Some(2))) should be (CP87(3))
    }
  }

}
