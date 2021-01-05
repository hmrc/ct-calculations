/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.{CP118, CP235}
import uk.gov.hmrc.ct.ct600.v2.B122

class SummaryLossesArisingThisPeriodCalculatorSpec extends WordSpec with Matchers {

  "SummaryLossesArisingThisPeriodCalculator" should {
    "return None if Adjusted trading profit or loss equals zero" in new SummaryLossesArisingThisPeriodCalculator {
      summaryLossesArisingThisPeriodCalculation(cp118 = CP118(0)) shouldBe CP235(None)
    }
    "return Some(1) if Adjusted trading loss = 1" in new SummaryLossesArisingThisPeriodCalculator {
      summaryLossesArisingThisPeriodCalculation(cp118 = CP118(1)) shouldBe CP235(Some(1))
    }
    "return Some(100) if Adjusted trading loss equals -100" in new SummaryLossesArisingThisPeriodCalculator {
      summaryLossesArisingThisPeriodCalculation(cp118 = CP118(100)) shouldBe CP235(Some(100))
    }
  }

  "SummaryLossesTradingArisingCalculator" should {
    "return Some(0) if Adjusted trading loss equals to 0" in new SummaryLossesArisingThisPeriodCalculator {
      summaryTradingLossesArisingCalculation(cp118 = CP118(0)) shouldBe B122(Some(0))
    }

    "return Some(100) if Adjusted trading loss equals 100" in new SummaryLossesArisingThisPeriodCalculator {
      summaryTradingLossesArisingCalculation(cp118 = CP118(100)) shouldBe B122(Some(100))
    }
  }
}
