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

  }
}
