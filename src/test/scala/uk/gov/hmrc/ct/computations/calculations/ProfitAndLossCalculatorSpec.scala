/*
 * Copyright 2022 HM Revenue & Customs
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

class ProfitAndLossCalculatorSpec extends WordSpec with Matchers {

  "Calculating Gross Profit Or Loss Before Tax (CP44)" should {
    "return a positive number when profit is greater than costs" in new ProfitAndLossCalculator {
      def calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp14: CP14 = CP14(100),
                                                            cp40: CP40 = CP40(50),
                                                            cp43: CP43 = CP43(Some(0)),
                                                            cp501: CP501 = CP501(0),
                                                            cp502: CP502 = CP502(Some(0)),
                                                            cp130: CP130 = CP130(0)) = calculateGrossProfitOrLossBeforeTax(cp14, cp40, cp43, cp501, cp502, cp130)

      calculateGrossProfitOrLossBeforeTaxWithBaseParams() shouldBe CP44(50)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp43 = CP43(Some(10))) shouldBe CP44(60)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp501 = CP501(20)) shouldBe CP44(70)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp502 = CP502(Some(30))) shouldBe CP44(80)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp502 = CP502(Some(10)), cp501 = CP501(20)) shouldBe CP44(80)
    }

    "return a negative number when profit is less than costs" in new ProfitAndLossCalculator {
      def calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp14: CP14 = CP14(50),
                                                            cp40: CP40 = CP40(100),
                                                            cp43: CP43 = CP43(Some(0)),
                                                            cp501: CP501 = CP501(0),
                                                            cp502: CP502 = CP502(Some(0)),
                                                            cp130: CP130 = CP130(0)) = calculateGrossProfitOrLossBeforeTax(cp14, cp40, cp43, cp501, cp502, cp130)


      calculateGrossProfitOrLossBeforeTaxWithBaseParams() shouldBe CP44(-50)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp43 = CP43(Some(10))) shouldBe CP44(-40)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp501 = CP501(20)) shouldBe CP44(-30)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp502 = CP502(Some(30))) shouldBe CP44(-20)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp502 = CP502(Some(10)), cp501 = CP501(20)) shouldBe CP44(-20)
    }

    "return zero when profit and costs are equal" in new ProfitAndLossCalculator {
      def calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp14: CP14 = CP14(100),
                                                            cp40: CP40 = CP40(100),
                                                            cp43: CP43 = CP43(Some(0)),
                                                            cp501: CP501 = CP501(0),
                                                            cp502: CP502 = CP502(Some(0)),
                                                            cp130: CP130 = CP130(0)) = calculateGrossProfitOrLossBeforeTax(cp14, cp40, cp43, cp501, cp502, cp130)


      calculateGrossProfitOrLossBeforeTaxWithBaseParams() shouldBe CP44(0)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp40 = CP40(110), cp43 = CP43(Some(10))) shouldBe CP44(0)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp40 = CP40(120), cp501 = CP501(20)) shouldBe CP44(0)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp40 = CP40(130), cp502 = CP502(Some(30))) shouldBe CP44(0)
      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp40 = CP40(130), cp43 = CP43(Some(10)), cp501 = CP501(20)) shouldBe CP44(0)
    }

    "Include cover support grants (Cp130) in CP44)" in new ProfitAndLossCalculator {
      def calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp14: CP14 = CP14(100),
                                                            cp40: CP40 = CP40(100),
                                                            cp43: CP43 = CP43(Some(0)),
                                                            cp501: CP501 = CP501(0),
                                                            cp502: CP502 = CP502(Some(0)),
                                                            cp130: CP130 = CP130(0)) = calculateGrossProfitOrLossBeforeTax(cp14, cp40, cp43, cp501, cp502, cp130)


      calculateGrossProfitOrLossBeforeTaxWithBaseParams(cp130 = CP130(20)) shouldBe CP44(20)
    }
  }
}
