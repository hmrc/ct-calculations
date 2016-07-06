package uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.abridged._

class OperatingProfitOrLossCalculatorSpec extends WordSpec with Matchers {

  "OperatingProfitOrLossCalculator" should {
    "calculating AC26" when {
      "return zero if all inputs are empty" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(None), AC18(None), AC20(None)) shouldBe AC26(0)
      }

      "return zero if all inputs are zero" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(0)), AC18(Some(0)), AC20(Some(0))) shouldBe AC26(0)
      }

      "return sum if all values positive" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(16)), AC18(Some(18)), AC20(Some(20))) shouldBe AC26(-22)
      }

      "return sum if values positive and negative" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(16)), AC18(Some(-18)), AC20(Some(20))) shouldBe AC26(14)
      }

      "return sum if all values negative" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(-16)), AC18(Some(-18)), AC20(Some(-20))) shouldBe AC26(22)
      }
    }
  }

  "OperatingProfitOrLossCalculator" should {
    "calculating AC27" when {
      "return zero if all inputs are empty" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(None), AC19(None), AC21(None)) shouldBe AC27(0)
      }

      "return zero if all inputs are zero" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(0)), AC19(Some(0)), AC21(Some(0))) shouldBe AC27(0)
      }

      "return sum if all values positive" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(16)), AC19(Some(18)), AC21(Some(20))) shouldBe AC27(-22)
      }

      "return sum if values positive and negative" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(16)), AC19(Some(-18)), AC21(Some(20))) shouldBe AC27(14)
      }

      "return sum if all values negative" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(-16)), AC19(Some(-18)), AC21(Some(-20))) shouldBe AC27(22)
      }
    }
  }
}
