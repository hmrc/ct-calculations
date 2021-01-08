/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait LossesBroughtForwardAgainstTradingProfitCalculator extends CtTypeConverters {

  def lossesBroughtForwardUsedAgainstTradingProfitCalculation(cpq17: CPQ17,
                                                              cp281: CP281,
                                                              cp282: CP282,
                                                              cp283a: CP283a,
                                                              cp283b: CP283b): CP283 = {
    val result = if (cp283a.hasValue || cp283b.hasValue) {
      Some(cp283a + cp283b)
    }
    else {
      for {
        cpq17 <- cpq17.value
        cp282 <- cp282.value if cpq17
      } yield cp282 min cp281.orZero
    }
    CP283(result)
  }

  def lossesBroughtForwardUsedAgainstTradingProfitForProfitsChargeable(cp283: CP283): CP290 = {
    CP290(cp283.value.filter(_ > 0))
  }
}
