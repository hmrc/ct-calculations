/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AC12, AC401, AC402, AC403, AC404}
import uk.gov.hmrc.ct.accounts.frs102.boxes._

class GrossProfitAndLossCalculatorSpec extends WordSpec with Matchers {

  "GrossProfitAndLossCalculator" should {

    "calculate AC24 whilst going through the Full journey" when {
      "all inputs are empty, AC24 is empty" in new GrossProfitAndLossCalculator {
        calculateAC24Full(AC12(None), AC401(None), AC14(None)) shouldBe AC24(None)
      }
      "return AC12 value as AC24 if AC12 and AC401 has value and AC14" in new GrossProfitAndLossCalculator { // 403
        calculateAC24Full(AC12(Some(12)), AC401(Some(10)), AC14(None)) shouldBe AC24(Some(22))
      }
      "return -AC14 value as AC16 if AC12 and AC401 are empty and AC14 and AC403 has a value" in new GrossProfitAndLossCalculator {
        calculateAC24Full(AC12(None), AC401(None), AC14(Some(14))) shouldBe AC24(Some(-14))
      }
      "return AC12 - AC14 value as AC16 if all have values" in new GrossProfitAndLossCalculator {
        calculateAC24Full(AC12(Some(12)), AC401(Some(20)), AC14(Some(14))) shouldBe AC24(Some(18))
      }
    }

    "calculate AC24 whilst going through the Abridged journey" when {
      "all inputs are empty, AC24 is empty" in new GrossProfitAndLossCalculator {
        calculateAC24Abridged(AC16(None), AC401(None)) shouldBe AC24(None)
      }
      "return AC12 value as AC24 if AC12 and AC401 has value and AC14 and AC403 are empty" in new GrossProfitAndLossCalculator {
        calculateAC24Abridged(AC16(Some(12)), AC401(Some(10))) shouldBe AC24(Some(22))
      }
      "AC16 and AC401 have value,  AC12 - AC14 value as AC24 if all have values" in new GrossProfitAndLossCalculator {
        calculateAC24Abridged(AC16(Some(12)), AC401(Some(20))) shouldBe AC24(Some(32)) // may need a deduction here
      }
    }

    "calculate AC25 whilst going through the Full journey" when {
      "all inputs are empty, AC25 is empty" in new GrossProfitAndLossCalculator {
        calculateAC25Full(AC13(None), AC402(None), AC15(None)) shouldBe AC25(None)
      }
      "AC13 and AC402 have value and AC15 is empty, add AC13 and AC402" in new GrossProfitAndLossCalculator {
        calculateAC25Full(AC13(Some(13)), AC402(Some(8)), AC15(None)) shouldBe AC25(Some(21))
      }
      "all inputs are empty apart from AC15, AC25 returns a negative number" in new GrossProfitAndLossCalculator {
        calculateAC25Full(AC13(None), AC402(None), AC15(Some(15))) shouldBe AC25(Some(-15))
      }
      "return AC13 - AC14 value as all have values" in new GrossProfitAndLossCalculator {
        calculateAC25Full(AC13(Some(13)), AC402(Some(7)), AC15(Some(15))) shouldBe AC25(Some(5))
        }
      }

    "calculate AC25 whilst going through the Abridged journey" when {
      "all inputs are empty, AC25 is empty" in new GrossProfitAndLossCalculator {
        calculateAC25Abridged(AC17(None), AC402(None)) shouldBe AC25(None)
      }
      "return AC17 value as AC25 if AC12 and AC402 has value" in new GrossProfitAndLossCalculator {
        calculateAC25Abridged(AC17(Some(13)), AC402(Some(8))) shouldBe AC25(Some(21))
      }
      "return AC17 - AC14 value as all have values" in new GrossProfitAndLossCalculator {
        calculateAC25Abridged(AC17(Some(13)), AC402(Some(7))) shouldBe AC25(Some(20))
      }
    }
  }
 }
