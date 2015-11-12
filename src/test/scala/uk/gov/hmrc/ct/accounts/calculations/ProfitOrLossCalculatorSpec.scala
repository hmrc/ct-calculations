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

  }
}
