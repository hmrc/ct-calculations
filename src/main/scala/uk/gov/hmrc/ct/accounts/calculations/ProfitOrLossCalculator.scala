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

  def calculateCurrentOperatingProfitOrLoss(ac16: AC16, ac18: AC18,
                                            ac20: AC20, ac22: AC22): AC26 = {
    AC26(calculateOperatingProfitOrLoss(ac16.value, ac18.value, ac20.value, ac22.value))
  }

  def calculatePreviousOperatingProfitOrLoss(ac17: AC17, ac19: AC19,
                                             ac21: AC21, ac23: AC23): AC27 = {
    AC27(calculateOperatingProfitOrLoss(ac17.value, ac19.value, ac21.value, ac23.value))
  }

  private def calculateProfitOrLoss(turnover: Option[Int], costs: Option[Int]): Option[Int] = {
    (turnover, costs) match {
      case (None, None) => None
      case (t, c) => Some(t.getOrElse(0) - c.getOrElse(0))
    }
  }

  private def calculateOperatingProfitOrLoss(grossProfit: Option[Int],
                                             distributionCosts: Option[Int],
                                             administrativeExpenses: Option[Int],
                                             otherOperatingIncome: Option[Int]): Option[Int] = {
    (grossProfit, distributionCosts, administrativeExpenses, otherOperatingIncome) match {
      case (None, _, _, _) => None
      case (gp, dc, ae, ooi) => Some(gp.getOrElse(0) - dc.getOrElse(0) - ae.getOrElse(0) + ooi.getOrElse(0))
    }
  }
}
