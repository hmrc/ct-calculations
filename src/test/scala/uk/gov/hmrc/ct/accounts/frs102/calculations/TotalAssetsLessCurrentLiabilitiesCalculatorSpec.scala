/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes._

class TotalAssetsLessCurrentLiabilitiesCalculatorSpec extends WordSpec with Matchers with TotalAssetsLessCurrentLiabilitiesCalculator {

  "TotalAssetsLessCurrentLiabilitiesCalculator" should {

    "calculate Current Total Assets Less Current Liabilities" in {
      val ac60 = AC60(Some(50))
      val ac48 = AC48(Some(20))
      calculateCurrentTotalAssetsLessCurrentLiabilities(ac60, ac48) shouldBe AC62(Some(70))
    }

    "return None for Current Total Assets Less Current Liabilities if all inputs are none" in {
      val ac60 = AC60(None)
      val ac48 = AC48(None)
      calculateCurrentTotalAssetsLessCurrentLiabilities(ac60, ac48) shouldBe AC62(None)
    }

    "calculate Previous Total Assets Less Current Liabilities" in {
      val ac61 = AC61(Some(50))
      val ac49 = AC49(Some(20))
      calculatePreviousTotalAssetsLessCurrentLiabilities(ac61, ac49) shouldBe AC63(Some(70))
    }

    "return None for Previous Total Assets Less Current Liabilities if all inputs are none" in {
      val ac61 = AC61(None)
      val ac49 = AC49(None)
      calculatePreviousTotalAssetsLessCurrentLiabilities(ac61, ac49) shouldBe AC63(None)
    }

  }
}
