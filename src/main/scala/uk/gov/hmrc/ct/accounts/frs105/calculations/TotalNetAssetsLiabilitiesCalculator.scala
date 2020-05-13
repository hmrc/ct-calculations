

package uk.gov.hmrc.ct.accounts.frs105.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs105.boxes._

trait TotalNetAssetsLiabilitiesCalculator extends DebitAwareCalculation {

  def calculateCurrentTotalNetAssetsLiabilities(ac62: AC62, ac64: AC64, ac66: AC66, ac470: AC470): AC68 = {
    sum(ac62, ac64, ac66, ac470)(AC68.apply)
  }

  def calculatePreviousTotalNetAssetsLiabilities(ac63: AC63, ac65: AC65, ac67: AC67, ac471: AC471): AC69 = {
    sum(ac63, ac65, ac67, ac471)(AC69.apply)
  }
}
