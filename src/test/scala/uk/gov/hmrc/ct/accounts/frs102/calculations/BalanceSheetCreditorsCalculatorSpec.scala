/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes._

class BalanceSheetCreditorsCalculatorSpec extends WordSpec with Matchers {

  "BalanceSheetTangibleAssetsCalculator" should {
    
    "calculating AC162" when {
      "return None only if all inputs are empty" in new BalanceSheetCreditorsCalculator {
        calculateAC162(AC156(None), AC158(None), AC160(None)) shouldBe AC162(None)
      }

      "return zero if net value is 0" in new BalanceSheetCreditorsCalculator {
        calculateAC162(AC156(Some(10)), AC158(Some(-5)), AC160(Some(-5))) shouldBe AC162(Some(0))
      }

      "return correct positive value" in new BalanceSheetCreditorsCalculator {
        calculateAC162(AC156(Some(10)), AC158(Some(9)), AC160(Some(39))) shouldBe AC162(Some(58))
      }

      "return correct negative value" in new BalanceSheetCreditorsCalculator {
        calculateAC162(AC156(Some(10)), AC158(Some(-10)), AC160(Some(-10))) shouldBe AC162(Some(-10))
      }
    }

    "calculating AC163" when {
      "return None only if all inputs are empty" in new BalanceSheetCreditorsCalculator {
        calculateAC163(AC157(None), AC159(None), AC161(None)) shouldBe AC163(None)
      }

      "return zero if net value is 0" in new BalanceSheetCreditorsCalculator {
        calculateAC163(AC157(Some(10)), AC159(Some(-5)), AC161(Some(-5))) shouldBe AC163(Some(0))
      }

      "return correct positive value" in new BalanceSheetCreditorsCalculator {
        calculateAC163(AC157(Some(10)), AC159(Some(9)), AC161(Some(39))) shouldBe AC163(Some(58))
      }

      "return correct negative value" in new BalanceSheetCreditorsCalculator {
        calculateAC163(AC157(Some(10)), AC159(Some(-10)), AC161(Some(-10))) shouldBe AC163(Some(-10))
      }
    }
    
  }
}
