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
import uk.gov.hmrc.ct.computations.{CP43, CP502, CP509, CP510}

class NonTradeIncomeCalculatorSpec extends WordSpec with Matchers {

  "computeNonTradeIncome" should {
    "return CP43 + CP502 + CP509 + CP510" in new NonTradeIncomeCalculator {
      nonTradeIncomeCalculation(cp43 = CP43(Some(12)), cp502 = CP502(Some(14)), cp509 = CP509(12), cp510 = CP510(Some(16))) shouldBe CATO01(54)
    }
    "return CP43 + CP502 + CP509 + CP510 when all zero" in new NonTradeIncomeCalculator {
      nonTradeIncomeCalculation(cp43 = CP43(Some(0)), cp502 = CP502(Some(0)), cp509 = CP509(0), cp510 = CP510(Some(0))) shouldBe CATO01(0)
    }
  }
}
