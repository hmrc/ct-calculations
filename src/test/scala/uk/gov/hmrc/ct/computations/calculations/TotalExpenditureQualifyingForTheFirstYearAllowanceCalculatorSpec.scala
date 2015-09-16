package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP79, CP80, CP81}

class TotalExpenditureQualifyingForTheFirstYearAllowanceCalculatorSpec extends WordSpec with Matchers {

  "TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator" should {
    "calculate the total expenditure qualifying for the first year allowance with populated values" in new TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator {
      totalExpenditureQualifyingForTheFirstYearAllowance(cp79 = CP79(1),
                                                         cp80 = CP80(2)) shouldBe CP81(3)
    }
  }
}