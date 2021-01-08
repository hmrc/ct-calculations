/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP79, CP80, CP81, CPAux1}

class TotalExpenditureQualifyingForTheFirstYearAllowanceCalculatorSpec extends WordSpec with Matchers {

  "TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator" should {
    "calculate the total expenditure qualifying for the first year allowance with populated values" in new TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator {
      totalExpenditureQualifyingForTheFirstYearAllowance(cp79 = CP79(Some(1)),
                                                         cpAux1 = CPAux1(2)) shouldBe CP81(3)
    }

    "calculate the total expenditure qualifying for the first year allowance without values" in new TotalExpenditureQualifyingForTheFirstYearAllowanceCalculator {
      totalExpenditureQualifyingForTheFirstYearAllowance(CP79(None), CPAux1(0)) shouldBe CP81(0)
    }
  }
}
