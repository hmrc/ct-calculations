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

package uk.gov.hmrc.ct.accounts.frsse2008.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.AC12
import uk.gov.hmrc.ct.{MicroEntityFiling, StatutoryAccountsFiling}
import uk.gov.hmrc.ct.accounts.frsse2008._

class ProfitOrLossCalculatorSpec extends WordSpec with Matchers with ProfitOrLossCalculator {

  "ProfitOrLossCalculatorSpec" should {

    "calculatePreviousGrossProfitOrLoss" when {
      "return None if not statutory accounts" in {
        calculatePreviousGrossProfitOrLoss(ac13 = AC13(Some(100)), ac15 = AC15(None), StatutoryAccountsFiling(false)) shouldBe AC17(None)
      }
      "return None if statutory accounts and AC13 and AC15 are None" in {
        calculatePreviousGrossProfitOrLoss(ac13 = AC13(None), ac15 = AC15(None), StatutoryAccountsFiling(true)) shouldBe AC17(None)
      }
      "return negative value of AC15 if statutory accounts and AC13 is None and AC15 has a value" in {
        calculatePreviousGrossProfitOrLoss(ac13 = AC13(None), ac15 = AC15(Some(123)), StatutoryAccountsFiling(true)) shouldBe AC17(Some(-123))
      }
      "return value of AC13 if statutory accounts and AC15 is None and AC13 has a value" in {
        calculatePreviousGrossProfitOrLoss(ac13 = AC13(Some(321)), ac15 = AC15(None), StatutoryAccountsFiling(true)) shouldBe AC17(Some(321))
      }
      "return value of AC13 - AC15 if statutory accounts and AC15 and AC13 have values" in {
        calculatePreviousGrossProfitOrLoss(ac13 = AC13(Some(321)), ac15 = AC15(Some(300)), StatutoryAccountsFiling(true)) shouldBe AC17(Some(21))
      }
    }

    "calculateCurrentGrossProfitOrLoss" when {
      "return None if not statutory accounts" in {
        calculateCurrentGrossProfitOrLoss(ac12 = AC12(Some(100)), ac14 = AC14(None), StatutoryAccountsFiling(false)) shouldBe AC16(None)
      }
      "return None if statutory accounts and AC12 and AC14 are None" in {
        calculateCurrentGrossProfitOrLoss(ac12 = AC12(None), ac14 = AC14(None), StatutoryAccountsFiling(true)) shouldBe AC16(None)
      }
      "return negative value of AC15 if statutory accounts and AC13 is None and AC15 has a value" in {
        calculateCurrentGrossProfitOrLoss(ac12 = AC12(None), ac14 = AC14(Some(123)), StatutoryAccountsFiling(true)) shouldBe AC16(Some(-123))
      }
      "return value of AC13 if statutory accounts and AC15 is None and AC13 has a value" in {
        calculateCurrentGrossProfitOrLoss(ac12 = AC12(Some(321)), ac14 = AC14(None), StatutoryAccountsFiling(true)) shouldBe AC16(Some(321))
      }
      "return value of AC13 - AC15 if statutory accounts and AC15 and AC13 have values" in {
        calculateCurrentGrossProfitOrLoss(ac12 = AC12(Some(321)), ac14 = AC14(Some(21)), StatutoryAccountsFiling(true)) shouldBe AC16(Some(300))
      }
    }

    "calculateCurrentOperatingProfitOrLoss" when {
      "return None if ac16 is None" in {
        calculateCurrentOperatingProfitOrLoss(ac16 = AC16(None), ac18 = AC18(Some(12)), ac20 = AC20(None), ac22 = AC22(None)) shouldBe AC26(None)
      }
      "return AC16 if all other parameters are empty" in {
        calculateCurrentOperatingProfitOrLoss(ac16 = AC16(Some(100)), ac18 = AC18(None), ac20 = AC20(None), ac22 = AC22(None)) shouldBe AC26(Some(100))
      }
      "return value of AC16 plus ac22 minus the sum of all other parameters resulting in a profit" in {
        calculateCurrentOperatingProfitOrLoss(ac16 = AC16(Some(100)), ac18 = AC18(Some(50)), ac20 = AC20(Some(40)), ac22 = AC22(Some(1))) shouldBe AC26(Some(11))
      }
      "return value of AC16 plus ac22 minus the sum of all other parameters resulting in a loss" in {
        calculateCurrentOperatingProfitOrLoss(ac16 = AC16(Some(100)), ac18 = AC18(Some(50)), ac20 = AC20(Some(100)), ac22 = AC22(Some(1))) shouldBe AC26(Some(-49))
      }
    }

    "calculatePreviousOperatingProfitOrLoss" when {
      "return None ac17 is None" in {
        calculatePreviousOperatingProfitOrLoss(ac17 = AC17(None), ac19 = AC19(Some(12)), ac21 = AC21(None), ac23 = AC23(None)) shouldBe AC27(None)
      }
      "return AC17 if all other parameters are empty" in {
        calculatePreviousOperatingProfitOrLoss(ac17 = AC17(Some(100)), ac19 = AC19(None), ac21 = AC21(None), ac23 = AC23(None)) shouldBe AC27(Some(100))
      }
      "return value of AC17 plus ac22 minus the sum of all other parameters resulting in a profit" in {
        calculatePreviousOperatingProfitOrLoss(ac17 = AC17(Some(100)), ac19 = AC19(Some(50)), ac21 = AC21(Some(40)), ac23 = AC23(Some(1))) shouldBe AC27(Some(11))
      }
      "return value of AC17 plus ac22 minus the sum of all other parameters resulting in a loss" in {
        calculatePreviousOperatingProfitOrLoss(ac17 = AC17(Some(100)), ac19 = AC19(Some(50)), ac21 = AC21(Some(100)), ac23 = AC23(Some(1))) shouldBe AC27(Some(-49))
      }
    }

    "calculateCurrentProfitOrLossBeforeTax" when {
      "return None if ac26 is None" in {
        calculateCurrentProfitOrLossBeforeTax(ac26 = AC26(None), ac28 = AC28(Some(12)), ac30 = AC30(None)) shouldBe AC32(None)
      }
      "return AC26 if all other parameters are empty" in {
        calculateCurrentProfitOrLossBeforeTax(ac26 = AC26(Some(100)), ac28 = AC28(None), ac30 = AC30(None)) shouldBe AC32(Some(100))
      }
      "return value of AC26 plus ac28 minus ac30" in {
        calculateCurrentProfitOrLossBeforeTax(ac26 = AC26(Some(100)), ac28 = AC28(Some(50)), ac30 = AC30(Some(40))) shouldBe AC32(Some(110))
      }
      "return value of AC16 plus ac28 minus ac30 resulting in a loss" in {
        calculateCurrentProfitOrLossBeforeTax(ac26 = AC26(Some(100)), ac28 = AC28(Some(50)), ac30 = AC30(Some(200))) shouldBe AC32(Some(-50))
      }
    }

    "calculatePreviousProfitOrLossBeforeTax" when {
      "return None if ac27 is None" in {
        calculatePreviousProfitOrLossBeforeTax(ac27 = AC27(None), ac29 = AC29(Some(12)), ac31 = AC31(None)) shouldBe AC33(None)
      }
      "return AC27 if all other parameters are empty" in {
        calculatePreviousProfitOrLossBeforeTax(ac27 = AC27(Some(100)), ac29 = AC29(None), ac31 = AC31(None)) shouldBe AC33(Some(100))
      }
      "return value of AC27 plus ac29 minus ac31" in {
        calculatePreviousProfitOrLossBeforeTax(ac27 = AC27(Some(100)), ac29 = AC29(Some(50)), ac31 = AC31(Some(40))) shouldBe AC33(Some(110))
      }
      "return value of AC26 plus ac28 minus ac30 resulting in a loss" in {
        calculatePreviousProfitOrLossBeforeTax(ac27 = AC27(Some(100)), ac29 = AC29(Some(50)), ac31 = AC31(Some(200))) shouldBe AC33(Some(-50))
      }
    }

    "calculateCurrentProfitOrLossAfterTax" when {
      "return None if ac26 is None" in {
        calculateCurrentProfitOtLossAfterTax(ac32 = AC32(None), ac34 = AC34(Some(12344))) shouldBe AC36(None)
      }
      "return AC26 if all other parameters are empty" in {
        calculateCurrentProfitOtLossAfterTax(ac32 = AC32(Some(100)), ac34 = AC34(None)) shouldBe AC36(Some(100))
      }
      "return value of AC26 plus ac28 minus ac30" in {
        calculateCurrentProfitOtLossAfterTax(ac32 = AC32(Some(100)), ac34 = AC34(Some(50))) shouldBe AC36(Some(50))
      }
      "return value of AC16 plus ac28 minus ac30 resulting in a loss" in {
        calculateCurrentProfitOtLossAfterTax(ac32 = AC32(Some(100)), ac34 = AC34(Some(150))) shouldBe AC36(Some(-50))
      }
    }

    "calculatePreviousProfitOrLossAfterTax" when {
      "return None if ac33 is None" in {
        calculatePreviousProfitOtLossAfterTax(ac33 = AC33(None), ac35 = AC35(Some(12344))) shouldBe AC37(None)
      }
      "return AC33 if all other parameters are empty" in {
        calculatePreviousProfitOtLossAfterTax(ac33 = AC33(Some(100)), ac35 = AC35(None)) shouldBe AC37(Some(100))
      }
      "return value of AC33 minus ac35" in {
        calculatePreviousProfitOtLossAfterTax(ac33 = AC33(Some(100)), ac35 = AC35(Some(50))) shouldBe AC37(Some(50))
      }
      "return value of AC33 minus ac35 resulting in a loss" in {
        calculatePreviousProfitOtLossAfterTax(ac33 = AC33(Some(100)), ac35 = AC35(Some(150))) shouldBe AC37(Some(-50))
      }
    }

    "calculateCurrentNetBalance" when {
      "return None if ac36 is None" in {
        calculateCurrentNetBalance(ac36 = AC36(None), ac38 = AC38(Some(12344))) shouldBe AC40(None)
      }
      "return AC36 if all other parameters are empty" in {
        calculateCurrentNetBalance(ac36 = AC36(Some(100)), ac38 = AC38(None)) shouldBe AC40(Some(100))
      }
      "return value of AC36 minus ac38" in {
        calculateCurrentNetBalance(ac36 = AC36(Some(100)), ac38 = AC38(Some(50))) shouldBe AC40(Some(50))
      }
      "return value of AC36 minus ac38 resulting in a loss" in {
        calculateCurrentNetBalance(ac36 = AC36(Some(100)), ac38 = AC38(Some(150))) shouldBe AC40(Some(-50))
      }
    }

    "calculatePreviousNetBalance" when {
      "return None if ac37 is None" in {
        calculatePreviousNetBalance(ac37 = AC37(None), ac39 = AC39(Some(12344))) shouldBe AC41(None)
      }
      "return AC37 if all other parameters are empty" in {
        calculatePreviousNetBalance(ac37 = AC37(Some(100)), ac39 = AC39(None)) shouldBe AC41(Some(100))
      }
      "return value of AC37 minus ac39" in {
        calculatePreviousNetBalance(ac37 = AC37(Some(100)), ac39 = AC39(Some(50))) shouldBe AC41(Some(50))
      }
      "return value of AC37 minus ac39 resulting in a loss" in {
        calculatePreviousNetBalance(ac37 = AC37(Some(100)), ac39 = AC39(Some(150))) shouldBe AC41(Some(-50))
      }
    }

    "calculatePreviousProfitOrLoss" when {
      "return None if not micro entity accounts" in {
        calculatePreviousProfitOrLoss(ac13 = AC13(Some(100)), ac406 = AC406(None),
                                      ac411 = AC411(None), ac416 = AC416(None),
                                      ac421 = AC421(None), ac426 = AC426(None),
                                      ac35 = AC35(None), microEntityFiling = MicroEntityFiling(false)) shouldBe AC436(None)
      }
      "return None if micro entity accounts and ac13 and cp15 are None" in {
        calculatePreviousProfitOrLoss(ac13 = AC13(None), ac406 = AC406(None),
                                      ac411 = AC411(None), ac416 = AC416(None),
                                      ac421 = AC421(None), ac426 = AC426(None),
                                      ac35 = AC35(None), microEntityFiling = MicroEntityFiling(true)) shouldBe AC436(None)
      }
      "return negative sum(AC416 -> AC35) if micro entity accounts and AC13 is 0 and AC406 is 0" in {
        calculatePreviousProfitOrLoss(ac13 = AC13(Some(0)), ac406 = AC406(Some(0)),
                                      ac411 = AC411(Some(10)), ac416 = AC416(Some(20)),
                                      ac421 = AC421(Some(30)), ac426 = AC426(Some(40)),
                                      ac35 = AC35(Some(50)), microEntityFiling = MicroEntityFiling(true)) shouldBe AC436(Some(-150))
      }
      "return sum(AC13 and AC406) - sum(AC416 -> AC35) if micro entity accounts" in {
        calculatePreviousProfitOrLoss(ac13 = AC13(Some(1000)), ac406 = AC406(Some(200)),
                                      ac411 = AC411(Some(10)), ac416 = AC416(Some(20)),
                                      ac421 = AC421(Some(30)), ac426 = AC426(Some(40)),
                                      ac35 = AC35(Some(50)), microEntityFiling = MicroEntityFiling(true)) shouldBe AC436(Some(1050))
      }

      "return sum of everything else when ac13 is None" in {
        calculatePreviousProfitOrLoss(ac13 = AC13(None), ac406 = AC406(Some(406)),
          ac411 = AC411(Some(411)), ac416 = AC416(Some(416)),
          ac421 = AC421(Some(421)), ac426 = AC426(Some(426)),
          ac35 = AC35(None), microEntityFiling = MicroEntityFiling(true)) shouldBe AC436(Some(-1268))
      }
      "return sum of everything with large ac13" in {
        calculatePreviousProfitOrLoss(ac13 = AC13(Some(24000)), ac406 = AC406(Some(406)),
          ac411 = AC411(Some(411)), ac416 = AC416(Some(416)),
          ac421 = AC421(Some(421)), ac426 = AC426(Some(426)),
          ac35 = AC35(None), microEntityFiling = MicroEntityFiling(true)) shouldBe AC436(Some(22732))
      }
      "return sum of everything else when ac406 is None" in {
        calculatePreviousProfitOrLoss(ac13 = AC13(Some(13)), ac406 = AC406(None),
                                      ac411 = AC411(Some(411)), ac416 = AC416(Some(416)),
                                      ac421 = AC421(Some(421)), ac426 = AC426(Some(426)),
                                      ac35 = AC35(None), microEntityFiling = MicroEntityFiling(true)) shouldBe AC436(Some(-1661))
      }
      "return sum of everything else when ac13 and AC406 are None" in {
        calculatePreviousProfitOrLoss(ac13 = AC13(None), ac406 = AC406(None),
                                      ac411 = AC411(Some(411)), ac416 = AC416(Some(416)),
                                      ac421 = AC421(Some(421)), ac426 = AC426(Some(426)),
                                      ac35 = AC35(None), microEntityFiling = MicroEntityFiling(true)) shouldBe AC436(Some(-1674))
      }
    }

    "calculateCurrentProfitOrLoss" when {
      "return None if not micro entity accounts" in {
        calculateCurrentProfitOrLoss(ac12 = AC12(Some(100)), ac405 = AC405(None),
                                      ac410 = AC410(None), ac415 = AC415(None),
                                      ac420 = AC420(None), ac425 = AC425(None),
                                      ac34 = AC34(None), microEntityFiling = MicroEntityFiling(false)) shouldBe AC435(None)
      }
      "return None if micro entity accounts and all fields are None" in {
        calculateCurrentProfitOrLoss(ac12 = AC12(None), ac405 = AC405(None),
                                      ac410 = AC410(None), ac415 = AC415(None),
                                      ac420 = AC420(None), ac425 = AC425(None),
                                      ac34 = AC34(None), microEntityFiling = MicroEntityFiling(true)) shouldBe AC435(None)
      }
      "return sum of everything else when ac12 is None" in {
        calculateCurrentProfitOrLoss(ac12 = AC12(None), ac405 = AC405(Some(405)),
                                      ac410 = AC410(Some(410)), ac415 = AC415(Some(415)),
                                      ac420 = AC420(Some(420)), ac425 = AC425(Some(425)),
                                      ac34 = AC34(None), microEntityFiling = MicroEntityFiling(true)) shouldBe AC435(Some(-1265))
      }
      "return sum of everything with large ac12" in {
        calculateCurrentProfitOrLoss(ac12 = AC12(12000), ac405 = AC405(Some(405)),
                                      ac410 = AC410(Some(410)), ac415 = AC415(Some(415)),
                                      ac420 = AC420(Some(420)), ac425 = AC425(Some(425)),
                                      ac34 = AC34(None), microEntityFiling = MicroEntityFiling(true)) shouldBe AC435(Some(10735))
      }
      "return sum of everything else when ac405 is None" in {
        calculateCurrentProfitOrLoss(ac12 = AC12(12), ac405 = AC405(None),
                                      ac410 = AC410(Some(410)), ac415 = AC415(Some(415)),
                                      ac420 = AC420(Some(420)), ac425 = AC425(Some(425)),
                                      ac34 = AC34(None), microEntityFiling = MicroEntityFiling(true)) shouldBe AC435(Some(-1658))
      }
      "return sum of everything else when ac12 and AC405 are None" in {
        calculateCurrentProfitOrLoss(ac12 = AC12(None), ac405 = AC405(None),
                                      ac410 = AC410(Some(410)), ac415 = AC415(Some(415)),
                                      ac420 = AC420(Some(420)), ac425 = AC425(Some(425)),
                                      ac34 = AC34(None), microEntityFiling = MicroEntityFiling(true)) shouldBe AC435(Some(-1670))
      }
      "return negative sum(AC415 -> AC34) if micro entity accounts and AC12 is 0 and AC405 is 0" in {
        calculateCurrentProfitOrLoss(ac12 = AC12(Some(0)), ac405 = AC405(Some(0)),
                                      ac410 = AC410(Some(10)), ac415 = AC415(Some(20)),
                                      ac420 = AC420(Some(30)), ac425 = AC425(Some(40)),
                                      ac34 = AC34(Some(50)), microEntityFiling = MicroEntityFiling(true)) shouldBe AC435(Some(-150))
      }
      "return sum(AC12 and AC405) - sum(AC415 -> AC34) if micro entity accounts" in {
        calculateCurrentProfitOrLoss(ac12 = AC12(Some(1000)), ac405 = AC405(Some(200)),
                                      ac410 = AC410(Some(10)), ac415 = AC415(Some(20)),
                                      ac420 = AC420(Some(30)), ac425 = AC425(Some(40)),
                                      ac34 = AC34(Some(50)), microEntityFiling = MicroEntityFiling(true)) shouldBe AC435(Some(1050))
      }
    }
  }
}
