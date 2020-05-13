

package uk.gov.hmrc.ct.accounts.frs102.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.boxes._

class TotalNetAssetsLiabilitiesCalculatorSpec extends WordSpec with Matchers with TotalNetAssetsLiabilitiesCalculator {

  "TotalNetAssetsLiabilitiesCalculator" should {

    "calculate current total Net Assets and Liabilities" in {
      calculateCurrentTotalNetAssetsLiabilities(AC62(Some(50)), AC64(Some(20)), AC66(Some(20)), AC150B(Some(20))) shouldBe AC68(Some(-10))
    }

    "return None for current total Net Assets and Liabilities when inputs are None" in {
      calculateCurrentTotalNetAssetsLiabilities(AC62(None), AC64(None), AC66(None), AC150B(None)) shouldBe AC68(None)
    }

    "calculate previous total Net Assets and Liabilities" in {
      calculatePreviousTotalNetAssetsLiabilities(AC63(Some(50)), AC65(Some(20)), AC67(Some(20)), AC151B(Some(20))) shouldBe AC69(Some(-10))
    }

    "return None for previous total Net Assets and Liabilities when inputs are None" in {
      calculatePreviousTotalNetAssetsLiabilities(AC63(None), AC65(None), AC67(None), AC151B(None)) shouldBe AC69(None)
    }
  }

}
