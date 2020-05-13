

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait AdjustedTradingProfitOrLossCalculator extends CtTypeConverters {
  def calculateAdjustedTradingProfit(cp44: CP44,
                                     cp54: CP54,
                                     cp59: CP59,
                                     cp186: CP186,
                                     cp91: CP91,
                                     cp670: CP670,
                                     cp668: CP668,
                                     cp297: CP297,
                                     cpq19: CPQ19): CP117 = {
    CP117(profit(cp44, cp54, cp59, cp186, cp91, cp670, cp668, cp297, cpq19) max 0)
  }

  def calculateAdjustedTradingLoss(cp44: CP44,
                                   cp54: CP54,
                                   cp59: CP59,
                                   cp186: CP186,
                                   cp91: CP91,
                                   cp670: CP670,
                                   cp668: CP668,
                                   cp297: CP297,
                                   cpq19: CPQ19): CP118 = {
    CP118((profit(cp44, cp54, cp59, cp186, cp91, cp670, cp668, cp297, cpq19) min 0).abs)
  }

  private def profit(cp44: CP44,
                     cp54: CP54,
                     cp59: CP59,
                     cp186: CP186,
                     cp91: CP91,
                     cp670: CP670,
                     cp668: CP668,
                     cp297: CP297,
                     cpq19: CPQ19) = {
    cp44 + cp54 - cp59 - cp186 + cp91 + cp670 - cp668 - cp297
  }
}

trait AdjustedTradingProfitForPeriodCalculator extends CtTypeConverters {

  def adjustedTradingProfitForPeriodCalculation(cp117: CP117,
                                                cpq17: CPQ17): CP282 = {
    val result = cpq17.value match {
      case Some(true) => Some(cp117.value)
      case _ => None
    }
    CP282(result)
  }

}

trait AdjustedTradingProfitCalculator {

  def adjustedTradingProfitCalculation(cp117: CP117): CP289 = {
    CP289(if (cp117.value < 0) None else Some(cp117.value))
  }

  def adjustedTradingProfitCalculationNonOptional(cp117: CP117): CP256 = {
    val value = cp117.value
    CP256(if (value < 0) 0 else value)
  }
}
