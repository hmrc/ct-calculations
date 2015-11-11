package uk.gov.hmrc.ct.accounts.calculations

import uk.gov.hmrc.ct.StatutoryAccountsFiling
import uk.gov.hmrc.ct.accounts._

trait ProfitOrLossCalculator {

  def calculateCurrentProfitOrLoss(): AC435 = {
    ???
  }

  def calculatePreviousProfitOrLoss(): AC436 = {
    ???
  }

  def calculateCurrentGrossProfitOrLoss(ac12: AC12, ac14: AC14, statutoryAccountsFiling: StatutoryAccountsFiling): AC16 = {
    AC16(None)
  }

  def calculatePreviousGrossProfitOrLoss(ac13: AC13, ac15: AC15, statutoryAccountsFiling: StatutoryAccountsFiling): AC17 = {
    AC17(None)
  }
}
