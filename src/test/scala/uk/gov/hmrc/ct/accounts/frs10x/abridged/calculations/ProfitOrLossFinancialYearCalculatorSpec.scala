package uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.abridged._

class ProfitOrLossFinancialYearCalculatorSpec extends WordSpec with Matchers {

  "ProfitOrLossFinancialYearCalculator" should {
    "calculating AC36" when {
      "return zero if all inputs are empty" in new ProfitOrLossFinancialYearCalculator {
        calculateAC36(AC32(0), AC34(None)) shouldBe AC36(0)
      }

      "return zero if all inputs are zero" in new ProfitOrLossFinancialYearCalculator {
        calculateAC36(AC32(0), AC34(Some(0))) shouldBe AC36(0)
      }

      "return sum if all values positive" in new ProfitOrLossFinancialYearCalculator {
        calculateAC36(AC32(16), AC34(Some(18))) shouldBe AC36(-2)
      }

      "return sum if values positive and negative" in new ProfitOrLossFinancialYearCalculator {
        calculateAC36(AC32(16), AC34(Some(-18))) shouldBe AC36(34)
      }

      "return sum if all values negative" in new ProfitOrLossFinancialYearCalculator {
        calculateAC36(AC32(-16), AC34(Some(-18))) shouldBe AC36(2)
      }
    }
  }

  "ProfitOrLossFinancialYearCalculator" should {
    "calculating AC37" when {
      "return zero if all inputs are empty" in new ProfitOrLossFinancialYearCalculator {
        calculateAC37(AC33(0), AC35(None)) shouldBe AC37(0)
      }

      "return zero if all inputs are zero" in new ProfitOrLossFinancialYearCalculator {
        calculateAC37(AC33(0), AC35(Some(0))) shouldBe AC37(0)
      }

      "return sum if all values positive" in new ProfitOrLossFinancialYearCalculator {
        calculateAC37(AC33(16), AC35(Some(18))) shouldBe AC37(-2)
      }

      "return sum if values positive and negative" in new ProfitOrLossFinancialYearCalculator {
        calculateAC37(AC33(16), AC35(Some(-18))) shouldBe AC37(34)
      }

      "return sum if all values negative" in new ProfitOrLossFinancialYearCalculator {
        calculateAC37(AC33(-16), AC35(Some(-18))) shouldBe AC37(2)
      }
    }
  }
}
