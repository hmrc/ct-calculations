/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes.{AC23, _}
import uk.gov.hmrc.ct.accounts.frs105.boxes.AC25
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC16, AC17, AC24}

class OperatingProfitOrLossCalculatorSpec extends WordSpec with Matchers {

  "OperatingProfitOrLossCalculator" should {
    "calculating AC26 for abridged accounts" when {
      "return zero if all inputs are empty" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(None), AC18(None), AC20(None), AC24(None)) shouldBe AC26(None)
      }

      "return zero if all inputs are zero" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(0)), AC18(Some(0)), AC20(Some(0)), AC24(Some(0))) shouldBe AC26(Some(0))
      }

      "return sum if all values positive" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(16)), AC18(Some(18)), AC20(Some(20)), AC24(Some(10))) shouldBe AC26(Some(-12))
      }

      "return sum if values positive and negative" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(16)), AC18(Some(-18)), AC20(Some(20)), AC24(Some(10))) shouldBe AC26(Some(24))
      }

      "return sum if all values negative" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(-16)), AC18(Some(-18)), AC20(Some(-20)), AC24(Some(-20))) shouldBe AC26(Some(2))
      }
    }

    "calculating AC26 for full accounts" when {
      "return zero if all inputs are empty" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(None), AC18(None), AC20(None), AC24(None), AC22(None)) shouldBe AC26(None)
      }

      "return zero if all inputs are zero" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(0)), AC18(Some(0)), AC20(Some(0)), AC24(Some(0)), AC22(Some(0))) shouldBe AC26(Some(0))
      }

      "return sum if all values positive" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(16)), AC18(Some(18)), AC20(Some(20)), AC24(Some(11)), AC22(Some(220))) shouldBe AC26(Some(209))
      }

      "return sum if values positive and negative" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(16)), AC18(Some(-18)), AC20(Some(20)), AC24(Some(1)), AC22(Some(-22))) shouldBe AC26(Some(-7))
      }

      "return sum if all values negative" in new OperatingProfitOrLossCalculator {
        calculateAC26(AC16(Some(-16)), AC18(Some(-18)), AC20(Some(-20)), AC24(Some(-10)), AC22(Some(-44))) shouldBe AC26(Some(-32))
      }
    }
  }

  "OperatingProfitOrLossCalculator" should {
    "calculating AC27 for abridged accounts" when {
      "return zero if all inputs are empty" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(None), AC19(None), AC21(None), AC25(None)) shouldBe AC27(None)
      }

      "return zero if all inputs are zero" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(0)), AC19(Some(0)), AC21(Some(0)), AC25(Some(0))) shouldBe AC27(Some(0))
      }

      "return sum if all values positive" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(16)), AC19(Some(18)), AC21(Some(20)), AC25(Some(10))) shouldBe AC27(Some(-12))
      }

      "return sum if values positive and negative" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(16)), AC19(Some(-18)), AC21(Some(20)), AC25(Some(5))) shouldBe AC27(Some(19))
      }

      "return sum if all values negative" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(-16)), AC19(Some(-18)), AC21(Some(-20)), AC25(Some(-10))) shouldBe AC27(Some(12))
      }
    }

    "calculating AC27 for full accounts" when {
      "return zero if all inputs are empty" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(None), AC19(None), AC21(None), AC25(None), AC23(None)) shouldBe AC27(None)
      }

      "return zero if all inputs are zero" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(0)), AC19(Some(0)), AC21(Some(0)), AC25(Some(0)), AC23(Some(0))) shouldBe AC27(Some(0))
      }

      "return sum if all values positive" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(16)), AC19(Some(18)), AC21(Some(20)), AC25(Some(10)), AC23(Some(23))) shouldBe AC27(Some(11))
      }

      "return sum if values positive and negative" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(16)), AC19(Some(-18)), AC21(Some(20)), AC25(Some(10)), AC23(Some(23))) shouldBe AC27(Some(47))
      }

      "return sum if all values negative" in new OperatingProfitOrLossCalculator {
        calculateAC27(AC17(Some(-16)), AC19(Some(-18)), AC21(Some(-20)), AC25(Some(-9)), AC23(Some(-23))) shouldBe AC27(Some(-10))
      }
    }
  }
}