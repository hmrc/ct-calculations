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
import uk.gov.hmrc.ct.computations._

class TotalProfitsBeforeDeductionsCalculatorSpec extends WordSpec with Matchers {

  "NetTradingProfitCalculator" should {

    "return correct value when consider CP284 as zero when CP284 is -ve" in new TotalProfitsBeforeDeductionsCalculator {
      val result = computeTotalProfitsBeforeDeductionsAndReliefs(cp284 = CP284(Some(-10000)),
        cp58 = CP58(100),
        cp511 = CP511(50),
        cp502 = CP502(Some(10)))
      result shouldBe CP293(160)
    }

    "return correct value when all fields have values and trading profit is greater than zero" in new TotalProfitsBeforeDeductionsCalculator {
      val result = computeTotalProfitsBeforeDeductionsAndReliefs(cp284 = CP284(Some(1000)),
        cp58 = CP58(100),
        cp511 = CP511(50),
        cp502 = CP502(Some(10)))
      result shouldBe CP293(1160)
    }

    "return correct value when trading profit is not populated and defaulted to zero" in new TotalProfitsBeforeDeductionsCalculator {
      val result = computeTotalProfitsBeforeDeductionsAndReliefs(cp284 = CP284(None),
        cp58 = CP58(100),
        cp511 = CP511(50),
        cp502 = CP502(Some(10)))
      result shouldBe CP293(160)
    }

    "return correct value when all fields have values and trading profit is greater than zero and ancillary is not populated" in new TotalProfitsBeforeDeductionsCalculator {
      val result = computeTotalProfitsBeforeDeductionsAndReliefs(cp284 = CP284(Some(1000)),
        cp58 = CP58(100),
        cp511 = CP511(50),
        cp502 = CP502(None))
      result shouldBe CP293(1150)
    }
  }
}
