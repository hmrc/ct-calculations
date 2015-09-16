package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait LossesBroughtForwardAgainstTradingProfitCalculator extends CtTypeConverters {

  def lossesBroughtForwardUsedAgainstTradingProfitCalculation(cpq17: CPQ17,
                                                              cp281: CP281,
                                                              cp282: CP282): CP283 = {
    val result = for {
      cpq17 <- cpq17.value
      cp282 <- cp282.value if cpq17
    } yield cp282 min cp281.value.getOrElse(0)
    CP283(result)
  }

  def lossesBroughtForwardUsedAgainstTradingProfitForProfitsChargeable(cp283: CP283): CP290 = {
    CP290(cp283.value.filter(_ > 0))
  }
}
