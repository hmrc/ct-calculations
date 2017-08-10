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
import uk.gov.hmrc.ct.CATO13
import uk.gov.hmrc.ct.computations.{CP293, CP294, CP997}

class NetProfitsChargeableToCtWithoutDonationsCalculatorSpec extends WordSpec with Matchers {

  "NetProfitsChargeableToCtCalculator" should {
    "return CP293 - CP294 when CP293 > CP294" in new NetProfitsChargeableToCtWithoutDonationsCalculator {
      val result = calculateNetProfitsChargeableToCtWithoutDonations(CP293(100), CP294(20), CP997(10))
      result shouldBe CATO13(70)
    }
    "return 0 when CP293 = CP294" in new NetProfitsChargeableToCtWithoutDonationsCalculator {
      val result = calculateNetProfitsChargeableToCtWithoutDonations(CP293(100), CP294(90), CP997(10))
      result shouldBe CATO13(0)
    }
    "return 0 when CP293 < CP294" in new NetProfitsChargeableToCtWithoutDonationsCalculator {
      val result = calculateNetProfitsChargeableToCtWithoutDonations(CP293(100), CP294(200), CP997(10))
      result shouldBe CATO13(0)
    }
  }
}
