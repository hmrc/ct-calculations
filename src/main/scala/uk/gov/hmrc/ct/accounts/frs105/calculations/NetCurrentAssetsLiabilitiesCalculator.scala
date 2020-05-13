

package uk.gov.hmrc.ct.accounts.frs105.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs105.boxes._

trait NetCurrentAssetsLiabilitiesCalculator extends DebitAwareCalculation {

  def calculateCurrentNetCurrentAssetsLiabilities(ac455: AC455, ac465: AC465, ac58: AC58): AC60 = {
    sum(ac455, ac465, ac58)(AC60.apply)
  }

  def calculatePreviousNetCurrentAssetsLiabilities(ac456: AC456, ac466: AC466, ac59: AC59): AC61 = {
    sum(ac456, ac466, ac59)(AC61.apply)
  }

}
