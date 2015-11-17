/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.StatutoryAccountsFiling
import uk.gov.hmrc.ct.accounts._

class ProfitOrLossCalculatorSpec extends WordSpec with Matchers with ProfitOrLossCalculator {

  "ProfitOrLossCalculatorSpec" should {

    "calculatePreviousGrossProfitOrLoss" when {
      "return None if not statutory accounts" in {
        calculatePreviousGrossProfitOrLoss(ac13 = AC13(Some(100)), ac15 = AC15(None), StatutoryAccountsFiling(false)) shouldBe AC17(None)
      }
      "return None if statutory accounts and cp13 and cp15 are None" in {
        calculatePreviousGrossProfitOrLoss(ac13 = AC13(None), ac15 = AC15(None), StatutoryAccountsFiling(true)) shouldBe AC17(None)
      }
      "return negative value of CP15 if statutory accounts and cp13 is None and cp15 has a value" in {
        calculatePreviousGrossProfitOrLoss(ac13 = AC13(None), ac15 = AC15(Some(123)), StatutoryAccountsFiling(true)) shouldBe AC17(Some(-123))
      }
      "return value of CP13 if statutory accounts and cp15 is None and cp13 has a value" in {
        calculatePreviousGrossProfitOrLoss(ac13 = AC13(Some(321)), ac15 = AC15(None), StatutoryAccountsFiling(true)) shouldBe AC17(Some(321))
      }
      "return value of CP13 - CP15 if statutory accounts and cp15 and cp13 have values" in {
        calculatePreviousGrossProfitOrLoss(ac13 = AC13(Some(321)), ac15 = AC15(Some(300)), StatutoryAccountsFiling(true)) shouldBe AC17(Some(21))
      }
    }

    "calculateCurrentGrossProfitOrLoss" when {
      "return None if not statutory accounts" in {
        calculateCurrentGrossProfitOrLoss(ac12 = AC12(Some(100)), ac14 = AC14(None), StatutoryAccountsFiling(false)) shouldBe AC16(None)
      }
      "return None if statutory accounts and cp12 and cp14 are None" in {
        calculateCurrentGrossProfitOrLoss(ac12 = AC12(None), ac14 = AC14(None), StatutoryAccountsFiling(true)) shouldBe AC16(None)
      }
      "return negative value of CP15 if statutory accounts and cp13 is None and cp15 has a value" in {
        calculateCurrentGrossProfitOrLoss(ac12 = AC12(None), ac14 = AC14(Some(123)), StatutoryAccountsFiling(true)) shouldBe AC16(Some(-123))
      }
      "return value of CP13 if statutory accounts and cp15 is None and cp13 has a value" in {
        calculateCurrentGrossProfitOrLoss(ac12 = AC12(Some(321)), ac14 = AC14(None), StatutoryAccountsFiling(true)) shouldBe AC16(Some(321))
      }
      "return value of CP13 - CP15 if statutory accounts and cp15 and cp13 have values" in {
        calculateCurrentGrossProfitOrLoss(ac12 = AC12(Some(321)), ac14 = AC14(Some(21)), StatutoryAccountsFiling(true)) shouldBe AC16(Some(300))
      }
    }

    "calculateCurrentOperatingProfitOrLoss" when {
      "return None if ac16 is None" in {
        calculateCurrentOperatingProfitOrLoss(ac16 = AC16(None), ac18 = AC18(Some(12)), ac20 = AC20(None), ac22 = AC22(None)) shouldBe AC26(None)
      }
      "return CP16 if all other parameters are empty" in {
        calculateCurrentOperatingProfitOrLoss(ac16 = AC16(Some(100)), ac18 = AC18(None), ac20 = AC20(None), ac22 = AC22(None)) shouldBe AC26(Some(100))
      }
      "return value of CP16 plus ac22 minus the sum of all other parameters resulting in a profit" in {
        calculateCurrentOperatingProfitOrLoss(ac16 = AC16(Some(100)), ac18 = AC18(Some(50)), ac20 = AC20(Some(40)), ac22 = AC22(Some(1))) shouldBe AC26(Some(11))
      }
      "return value of CP16 plus ac22 minus the sum of all other parameters resulting in a loss" in {
        calculateCurrentOperatingProfitOrLoss(ac16 = AC16(Some(100)), ac18 = AC18(Some(50)), ac20 = AC20(Some(100)), ac22 = AC22(Some(1))) shouldBe AC26(Some(-49))
      }
    }

    "calculatePreviousOperatingProfitOrLoss" when {
      "return None ac17 is None" in {
        calculatePreviousOperatingProfitOrLoss(ac17 = AC17(None), ac19 = AC19(Some(12)), ac21 = AC21(None), ac23 = AC23(None)) shouldBe AC27(None)
      }
      "return CP17 if all other parameters are empty" in {
        calculatePreviousOperatingProfitOrLoss(ac17 = AC17(Some(100)), ac19 = AC19(None), ac21 = AC21(None), ac23 = AC23(None)) shouldBe AC27(Some(100))
      }
      "return value of CP17 plus ac22 minus the sum of all other parameters resulting in a profit" in {
        calculatePreviousOperatingProfitOrLoss(ac17 = AC17(Some(100)), ac19 = AC19(Some(50)), ac21 = AC21(Some(40)), ac23 = AC23(Some(1))) shouldBe AC27(Some(11))
      }
      "return value of CP17 plus ac22 minus the sum of all other parameters resulting in a loss" in {
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
  }
}
