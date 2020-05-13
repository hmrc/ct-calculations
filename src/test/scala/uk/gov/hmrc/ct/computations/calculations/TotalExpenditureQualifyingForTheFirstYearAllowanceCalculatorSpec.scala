/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP79, CP80, CP81}

class TotalExpenditureQualifyingForTheFirstYearAllowanceCalculatorSpec extends WordSpec with Matchers {

  "TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator" should {
    "calculate the total expenditure qualifying for the first year allowance with populated values" in new TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator {
      totalExpenditureQualifyingForTheFirstYearAllowance(cp79 = CP79(Some(1)),
                                                         cp80 = CP80(Some(2))) shouldBe CP81(3)
    }

    "calculate the total expenditure qualifying for the first year allowance without values" in new TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator {
      totalExpenditureQualifyingForTheFirstYearAllowance(CP79(None), CP80(None)) shouldBe CP81(0)
    }
  }
}
