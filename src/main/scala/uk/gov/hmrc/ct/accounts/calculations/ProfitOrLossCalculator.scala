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
    AC16(if (statutoryAccountsFiling.value) {
      calculateProfitOrLoss(ac12.value, ac14.value)
    }
    else None)
  }

  def calculatePreviousGrossProfitOrLoss(ac13: AC13, ac15: AC15, statutoryAccountsFiling: StatutoryAccountsFiling): AC17 = {
    AC17 (
      if (statutoryAccountsFiling.value) {
        calculateProfitOrLoss(ac13.value, ac15.value)
      }
      else None
    )
  }

  private def calculateProfitOrLoss(turnover: Option[Int], costs: Option[Int]): Option[Int] = {
    (turnover, costs) match {
      case (None, None) => None
      case (t, c) => Some(t.getOrElse(0) - c.getOrElse(0))
    }
  }
}
