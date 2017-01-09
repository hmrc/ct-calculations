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
import uk.gov.hmrc.ct.computations.{CP293, CP294, CP295, CP999}

class NetProfitsChargeableToCtCalculatorSpec extends WordSpec with Matchers {

  "NetProfitsChargeableToCtCalculator" should {

    "return correct value when all inputs are populated" in new NetProfitsChargeableToCtCalculator {
      val result = calculateNetProfitsChargeableToCt(totalProfitsBeforeDeductions = CP293(100),
        tradingLossesOfThisPeriodAndLaterPeriods = CP294(20), totalDonations = CP999(10))

      result shouldBe CP295(70)
    }
  }
}
