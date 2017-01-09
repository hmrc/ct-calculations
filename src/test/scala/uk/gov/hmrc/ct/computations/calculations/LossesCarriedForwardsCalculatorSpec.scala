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
import uk.gov.hmrc.ct.computations.CP288._
import uk.gov.hmrc.ct.computations._

class LossesCarriedForwardsCalculatorSpec extends WordSpec with Matchers {

  "LossesCarriedForwardsCalculator - Scenario 1 - CPQ17" should {
    "return None if CPQ17 is false (and both CPQ19 and CPQ20 are undefined)" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(false))) shouldBe CP288(None)
    }
    "return None when CPQ17 is not defined (and both CPQ19 and CPQ20 are undefined)" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq17 = CPQ17(None)) shouldBe CP288(None)
    }
    "return Zero if CPQ17 is true but both CP281 and CP283 are zero" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(true)), cp281 = CP281(Some(0)), cp283 = CP283(Some(0))) shouldBe CP288(Some(0))
    }
    "return Zero if CPQ17 is true but both CP281 and CP283 are undefined" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(true)), cp281 = CP281(None), cp283 = CP283(None)) shouldBe CP288(Some(0))
    }
    "return CP281 - CP283 when CPQ17 is true and CP281 > CP283" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(true)), cp281 = CP281(Some(1000)), cp283 = CP283(Some(200))) shouldBe CP288(Some(800))
    }
    "return Zero if CPQ17 is true and CP281 < CP283" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(true)), cp281 = CP281(Some(200)), cp283 = CP283(Some(1000))) shouldBe CP288(Some(0))
    }
  }
  "LossesCarriedForwardsCalculator - Scenario 3 - CPQ19 has been answered, we have losses (CP117 < 0 and CP118 > 0) and we have non trading profit (varA > 0)" should {
    "return CP118 when CPQ19 is No, CPQ20 is undefined and CP118 > 0" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq19 = CPQ19(Some(false)), cpq20 = CPQ20(None), cp118 = CP118(3)) shouldBe CP288(Some(3))
    }
    "return CP118 - CP998 - CP287 when CPQ19 is Yes, CPQ20 is Yes, CP118 > 0 and result of calculation is positive" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq19 = CPQ19(Some(true)), cpq20 = CPQ20(Some(true)), cp118 = CP118(1000), cp998 = CP998(Some(100)), cp287 = CP287(200)) shouldBe CP288(Some(700))
    }
    "return Zero when CPQ19 is Yes, CPQ20 is Yes and result of calculation (CP118 - CP998 - CP287) is negative" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq19 = CPQ19(Some(true)), cpq20 = CPQ20(Some(true)), cp118 = CP118(100), cp998 = CP998(Some(200)), cp287 = CP287(300)) shouldBe CP288(Some(0))
    }
    "return CP118 - CATO01 when CPQ19 is Yes, CPQ20 is No and CP118 > zero" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq19 = CPQ19(Some(true)), cpq20 = CPQ20(Some(false)), cp118 = CP118(1000), cato01 = CATO01(100)) shouldBe CP288(Some(900))
    }
    "return Zero when CPQ19 is Yes, CPQ20 is No and the result of calculation(CP118 - CATO01) is negative" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq19 = CPQ19(Some(true)), cpq20 = CPQ20(Some(false)), cp118 = CP118(100), cato01 = CATO01(200)) shouldBe CP288(Some(0))
    }
  }
  "LossesCarriedForwardsCalculator - Scenario 2 - CPQ20 entered (and CPQ17 and CPQ19 is undefined) CP118 > Zero and CATO01 is Zero" should {
    "when CPQ20 is Yes, CP118 > zero and result of calculation (CP118 - CP287) is positive return CP118 - CP287" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq20 = CPQ20(Some(true)), cp118 = CP118(1000), cp287 = CP287(300)) shouldBe CP288(Some(700))
    }
    "when CPQ20 is Yes and result of calculation (CP118 - CP287) is negative return Zero " in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq20 = CPQ20(Some(true)), cp118 = CP118(100), cp287 = CP287(300)) shouldBe CP288(Some(0))
    }
    "when CPQ20 is No and CP118 > Zero return CP118 " in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq20 = CPQ20(Some(false)), cp118 = CP118(100)) shouldBe CP288(Some(100))
    }
  }

  "LossesCarriedForwardsCalculator - Invalid Scenarios" should {
    "return None when CPQ17 is false" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(false)), cpq19 = CPQ19(None),  cpq20 = CPQ20(None)) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(false)), cpq19 = CPQ19(Some(true)),  cpq20 = CPQ20(None)) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(false)), cpq19 = CPQ19(Some(false)),  cpq20 = CPQ20(None)) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(false)), cpq19 = CPQ19(None),  cpq20 = CPQ20(Some(true))) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(false)), cpq19 = CPQ19(None),  cpq20 = CPQ20(Some(false))) shouldBe CP288(None)
    }
    "return None when CPQ17 and CPQ19 are both defined" in new LossesCarriedForwardsCalculator {
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(false)), cpq19 = CPQ19(Some(false)),  cpq20 = CPQ20(None)) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(true)), cpq19 = CPQ19(Some(false)),  cpq20 = CPQ20(None)) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(false)), cpq19 = CPQ19(Some(true)),  cpq20 = CPQ20(None)) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(true)), cpq19 = CPQ19(Some(true)),  cpq20 = CPQ20(None)) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(false)), cpq19 = CPQ19(Some(false)),  cpq20 = CPQ20(Some(true))) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(true)), cpq19 = CPQ19(Some(false)),  cpq20 = CPQ20(Some(true))) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(false)), cpq19 = CPQ19(Some(true)),  cpq20 = CPQ20(Some(true))) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(true)), cpq19 = CPQ19(Some(true)),  cpq20 = CPQ20(Some(true))) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(false)), cpq19 = CPQ19(Some(false)),  cpq20 = CPQ20(Some(false))) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(true)), cpq19 = CPQ19(Some(false)),  cpq20 = CPQ20(Some(false))) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(false)), cpq19 = CPQ19(Some(true)),  cpq20 = CPQ20(Some(false))) shouldBe CP288(None)
      calculateLossesCarriedForward(cpq17 = CPQ17(Some(true)), cpq19 = CPQ19(Some(true)),  cpq20 = CPQ20(Some(false))) shouldBe CP288(None)
    }
  }

  private def calculateLossesCarriedForward(cpq17: CPQ17 = CPQ17(None),
                                            cpq19: CPQ19 = CPQ19(None),
                                            cpq20: CPQ20 = CPQ20(None),
                                            cp281: CP281 = CP281(Some(281)),
                                            cp118: CP118 = CP118(118),
                                            cp283: CP283 = CP283(Some(283)),
                                            cp998: CP998 = CP998(Some(998)),
                                            cp287: CP287 = CP287(Some(287)),
                                            cato01: CATO01 = CATO01(1)): CP288 =
     lossesCarriedForwardsCalculation(cpq17 = cpq17,
                                      cpq19 = cpq19,
                                      cpq20 = cpq20,
                                      cp281 = cp281,
                                      cp118 = cp118,
                                      cp283 = cp283,
                                      cp998 = cp998,
                                      cp287 = cp287,
                                      cato01 = cato01)
}
