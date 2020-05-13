/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes._

class TotalCurrentAssetsCalculatorSpec extends WordSpec with Matchers with TotalCurrentAssetsCalculator {

  "TotalCurrentAssetsCalculator" should {

    "total current-current assets" in {
      val ac50 = AC50(Some(50))
      val ac52 = AC52(Some(20))
      val ac54 = AC54(Some(30))
      calculateCurrentTotalCurrentAssets(ac50, ac52, ac54) shouldBe AC56(Some(100))
    }

    "return None for current total current assets when all inputs are None" in {
      val ac50 = AC50(None)
      val ac52 = AC52(None)
      val ac54 = AC54(None)
      calculateCurrentTotalCurrentAssets(ac50, ac52, ac54) shouldBe AC56(None)
    }

    "total previous-current assets" in {
      val ac51 = AC51(Some(50))
      val ac53 = AC53(Some(20))
      val ac55 = AC55(Some(30))
      calculatePreviousTotalCurrentAssets(ac51, ac53, ac55) shouldBe AC57(Some(100))
    }

    "return None for previous total current assets when all inputs are None" in {
      val ac51 = AC51(None)
      val ac53 = AC53(None)
      val ac55 = AC55(None)
      calculatePreviousTotalCurrentAssets(ac51, ac53, ac55) shouldBe AC57(None)
    }
    
  }

}
