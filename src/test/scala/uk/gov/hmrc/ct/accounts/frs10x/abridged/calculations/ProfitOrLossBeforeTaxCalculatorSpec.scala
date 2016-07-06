package uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.abridged._

class ProfitOrLossBeforeTaxCalculatorSpec extends WordSpec with Matchers {

  "ProfitOrLossBeforeTaxCalculator" should {
    "calculating AC32" when {
      "return zero if all inputs are empty" in new ProfitOrLossBeforeTaxCalculator {
        calculateAC32(AC26(0), AC28(None), AC30(None)) shouldBe AC32(0)
      }

      "return zero if all inputs are zero" in new ProfitOrLossBeforeTaxCalculator {
        calculateAC32(AC26(0), AC28(Some(0)), AC30(Some(0))) shouldBe AC32(0)
      }

      "return sum if all values positive" in new ProfitOrLossBeforeTaxCalculator {
        calculateAC32(AC26(16), AC28(Some(18)), AC30(Some(20))) shouldBe AC32(14)
      }

      "return sum if values positive and negative" in new ProfitOrLossBeforeTaxCalculator {
        calculateAC32(AC26(16), AC28(Some(-18)), AC30(Some(20))) shouldBe AC32(-22)
      }

      "return sum if all values negative" in new ProfitOrLossBeforeTaxCalculator {
        calculateAC32(AC26(-16), AC28(Some(-18)), AC30(Some(-20))) shouldBe AC32(-14)
      }
    }
  }

  "ProfitOrLossBeforeTaxCalculator" should {
    "calculating AC33" when {
      "return zero if all inputs are empty" in new ProfitOrLossBeforeTaxCalculator {
        calculateAC33(AC27(0), AC29(None), AC31(None)) shouldBe AC33(0)
      }

      "return zero if all inputs are zero" in new ProfitOrLossBeforeTaxCalculator {
        calculateAC33(AC27(0), AC29(Some(0)), AC31(Some(0))) shouldBe AC33(0)
      }

      "return sum if all values positive" in new ProfitOrLossBeforeTaxCalculator {
        calculateAC33(AC27(16), AC29(Some(18)), AC31(Some(20))) shouldBe AC33(14)
      }

      "return sum if values positive and negative" in new ProfitOrLossBeforeTaxCalculator {
        calculateAC33(AC27(16), AC29(Some(-18)), AC31(Some(20))) shouldBe AC33(-22)
      }

      "return sum if all values negative" in new ProfitOrLossBeforeTaxCalculator {
        calculateAC33(AC27(-16), AC29(Some(-18)), AC31(Some(-20))) shouldBe AC33(-14)
      }
    }
  }
}
