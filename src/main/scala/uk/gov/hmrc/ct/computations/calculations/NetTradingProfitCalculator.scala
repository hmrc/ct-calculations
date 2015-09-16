package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP117, CP283, CP284, CP291}

trait NetTradingProfitCalculator extends CtTypeConverters {

  def netTradingProfitCalculation(cp117: CP117,
                                  cp283: CP283): CP284 = {
    CP284(Some(cp117.value - cp283.value.getOrElse(0)))
  }

  def netTradingProfitForProfitsChargeable(netTradingProfit: CP284, lossesBroughtForwardUsedAgainstTradingProfit: CP283): CP291 = {
    val result = lossesBroughtForwardUsedAgainstTradingProfit.value match {
      case Some(x) if x > 0 => netTradingProfit.value
      case _ => None
    }
    CP291(result)
  }
}
