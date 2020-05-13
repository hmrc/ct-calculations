/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes.loansToDirectors.{AC306A, AC307A, AC308A, AC309A}

class LoansToDirectorsCalculatorSpec extends WordSpec with Matchers {

  "LoansToDirectorsCalculator" should {
    "calculating AC309A" when {
      "return None only if all inputs are empty" in new LoansToDirectorsCalculator {
        calculateLoanBalanceAtEndOfPOA(AC306A(None), AC307A(None), AC308A(None)) shouldBe AC309A(None)
        calculateLoanBalanceAtEndOfPOA(AC306A(Some(0)), AC307A(None), AC308A(None)) shouldBe AC309A(Some(0))
        calculateLoanBalanceAtEndOfPOA(AC306A(None), AC307A(Some(0)), AC308A(None)) shouldBe AC309A(Some(0))
        calculateLoanBalanceAtEndOfPOA(AC306A(None), AC307A(None), AC308A(Some(0))) shouldBe AC309A(Some(0))
      }

      "return zero if net value is 0" in new LoansToDirectorsCalculator {
        calculateLoanBalanceAtEndOfPOA(AC306A(Some(0)), AC307A(Some(0)), AC308A(Some(0))) shouldBe AC309A(Some(0))
      }

      "return correct positive value" in new LoansToDirectorsCalculator {
        calculateLoanBalanceAtEndOfPOA(AC306A(Some(11)), AC307A(Some(13)), AC308A(Some(7))) shouldBe AC309A(Some(17))
      }

      "return correct negative value" in new LoansToDirectorsCalculator {
        calculateLoanBalanceAtEndOfPOA(AC306A(Some(11)), AC307A(Some(13)), AC308A(Some(27))) shouldBe AC309A(Some(-3))
      }
    }
  }
}
