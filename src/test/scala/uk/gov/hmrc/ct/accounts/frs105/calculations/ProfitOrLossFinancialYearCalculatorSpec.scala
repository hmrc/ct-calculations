/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs105.boxes._

class ProfitOrLossFinancialYearCalculatorSpec extends WordSpec with Matchers {

  "ProfitOrLossFinancialYearCalculator" should {
    "calculate AC435 and" when {
      "return empty AC435 if all boxes are empty" in new ProfitOrLossFinancialYearCalculator {
        calculateAC435(AC12(None), AC24(None), AC405(None), AC410(None), AC415(None), AC420(None), AC425(None), AC34(None), AC401(None), AC403(None)) shouldBe AC435(None)
      }
      "return sum of all boxes" in new ProfitOrLossFinancialYearCalculator {
        calculateAC435(AC12(Some(50)), AC24(Some(9)), AC405(Some(10)), AC410(Some(1)), AC415(Some(1)), AC420(Some(1)), AC425(Some(-1)), AC34(Some(1)), AC401(Some(10)), AC403(Some(5))) shouldBe AC435(Some(71))
      }
    }

    "calculate AC436 and" when {
      "return empty AC436 if all boxes are empty" in new ProfitOrLossFinancialYearCalculator {
        calculateAC436(AC13(None), AC25(None), AC406(None), AC411(None), AC416(None), AC421(None), AC426(None), AC35(None), AC402(None), AC404(None)) shouldBe AC436(None)
      }
      "return sum of all boxes" in new ProfitOrLossFinancialYearCalculator {
        calculateAC436(AC13(Some(50)), AC25(Some(9)), AC406(Some(10)), AC411(Some(1)), AC416(Some(1)), AC421(Some(1)), AC426(Some(-1)), AC35(Some(1)), AC402(Some(10)), AC404(Some(5))) shouldBe AC436(Some(71))
      }
    }
  }
}
