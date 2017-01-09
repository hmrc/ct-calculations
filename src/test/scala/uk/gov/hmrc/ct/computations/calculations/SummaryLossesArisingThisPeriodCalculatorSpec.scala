/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
