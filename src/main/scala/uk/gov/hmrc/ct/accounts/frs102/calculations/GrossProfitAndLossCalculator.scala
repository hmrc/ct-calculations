

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.AC12
import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs102.boxes._

trait GrossProfitAndLossCalculator extends DebitAwareCalculation {

  def calculateAC16(ac12: AC12, ac14: AC14): AC16 = {
    sum(ac12, ac14)(AC16.apply)
  }

  def calculateAC17(ac13: AC13, ac15: AC15): AC17= {
    sum(ac13, ac15)(AC17.apply)
  }
}
