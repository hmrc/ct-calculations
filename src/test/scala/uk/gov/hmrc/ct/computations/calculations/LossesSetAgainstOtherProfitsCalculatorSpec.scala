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
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.computations.{CP118, CP998, CPQ19}

class LossesSetAgainstOtherProfitsCalculatorSpec extends WordSpec with Matchers {

  "Losses Set Against Other Profits Calculator" should {
    "return CP118 when CP118 is less than CATO01 and CP118 is positive" in new LossesSetAgainstOtherProfitsCalculator {
      calculateLossesSetAgainstProfits(cato01 = CATO01(10), cp118 = CP118(9), cpq19 = CPQ19(Some(true))) shouldBe CP998(Some(9))
    }
    "return CATO01 when CATO01 is less CP118 and CP118 is positive" in new LossesSetAgainstOtherProfitsCalculator {
      calculateLossesSetAgainstProfits(cato01 = CATO01(10), cp118 = CP118(19), cpq19 = CPQ19(Some(true))) shouldBe CP998(Some(10))
    }
    "return CATO01 when CATO01 equals absolute CP118 and CP118 is positive" in new LossesSetAgainstOtherProfitsCalculator {
      calculateLossesSetAgainstProfits(cato01 = CATO01(10), cp118 = CP118(10), cpq19 = CPQ19(Some(true))) shouldBe CP998(Some(10))
    }
    "return None when CPQ19 is None" in new LossesSetAgainstOtherProfitsCalculator {
      calculateLossesSetAgainstProfits(cato01 = CATO01(10), cp118 = CP118(10), cpq19 = CPQ19(None)) shouldBe CP998(None)
    }
    "return None when CPQ19 is false" in new LossesSetAgainstOtherProfitsCalculator {
      calculateLossesSetAgainstProfits(cato01 = CATO01(10), cp118 = CP118(10), cpq19 = CPQ19(Some(false))) shouldBe CP998(None)
    }
  }
}
