/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes._

class TotalFixedAssetsCalculatorSpec extends WordSpec with Matchers with TotalFixedAssetsCalculator {

  "TotalFixedAssetsCalculator" should {

    "total current fixed assets" in {
      val ac42 = AC42(Some(50))
      val ac44 = AC44(Some(20))
      calculateCurrentTotalFixedAssets(ac42, ac44) shouldBe AC48(Some(70))
    }

    "return None for total current fixed assets when Intangible and Tangible Assets are None" in {
      val ac42 = AC42(None)
      val ac44 = AC44(None)
      calculateCurrentTotalFixedAssets(ac42, ac44) shouldBe AC48(None)
    }

    "total previous fixed assets" in {
      val ac43 = AC43(Some(50))
      val ac45 = AC45(Some(20))
      calculatePreviousTotalFixedAssets(ac43, ac45) shouldBe AC49(Some(70))
    }

    "return None for total previous fixed assets when Intangible and Tangible Assets are None" in {
      val ac43 = AC43(None)
      val ac45 = AC45(None)
      calculatePreviousTotalFixedAssets(ac43, ac45) shouldBe AC49(None)
    }
    
  }

}
