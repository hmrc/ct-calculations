

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes._

class ProfitOrLossFinancialYearCalculatorSpec extends WordSpec with Matchers {

  "ProfitOrLossFinancialYearCalculator" should {
    "calculating AC36" when {
      "return zero if all inputs are empty" in new ProfitOrLossFinancialYearCalculator {
        calculateAC36(AC32(None), AC34(None)) shouldBe AC36(None)
      }

      "return zero if all inputs are zero" in new ProfitOrLossFinancialYearCalculator {
        calculateAC36(AC32(Some(0)), AC34(Some(0))) shouldBe AC36(Some(0))
      }

      "return sum if all values positive" in new ProfitOrLossFinancialYearCalculator {
        calculateAC36(AC32(Some(16)), AC34(Some(18))) shouldBe AC36(Some(-2))
      }

      "return sum if values positive and negative" in new ProfitOrLossFinancialYearCalculator {
        calculateAC36(AC32(Some(16)), AC34(Some(-18))) shouldBe AC36(Some(34))
      }

      "return sum if all values negative" in new ProfitOrLossFinancialYearCalculator {
        calculateAC36(AC32(Some(-16)), AC34(Some(-18))) shouldBe AC36(Some(2))
      }
    }
  }

  "ProfitOrLossFinancialYearCalculator" should {
    "calculating AC37" when {
      "return zero if all inputs are empty" in new ProfitOrLossFinancialYearCalculator {
        calculateAC37(AC33(None), AC35(None)) shouldBe AC37(None)
      }

      "return zero if all inputs are zero" in new ProfitOrLossFinancialYearCalculator {
        calculateAC37(AC33(Some(0)), AC35(Some(0))) shouldBe AC37(Some(0))
      }

      "return sum if all values positive" in new ProfitOrLossFinancialYearCalculator {
        calculateAC37(AC33(Some(16)), AC35(Some(18))) shouldBe AC37(Some(-2))
      }

      "return sum if values positive and negative" in new ProfitOrLossFinancialYearCalculator {
        calculateAC37(AC33(Some(16)), AC35(Some(-18))) shouldBe AC37(Some(34))
      }

      "return sum if all values negative" in new ProfitOrLossFinancialYearCalculator {
        calculateAC37(AC33(Some(-16)), AC35(Some(-18))) shouldBe AC37(Some(2))
      }
    }
  }
}
