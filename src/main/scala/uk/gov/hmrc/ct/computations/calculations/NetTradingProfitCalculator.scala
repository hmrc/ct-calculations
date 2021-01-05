/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP117, CP283, CP284, CP291}

trait NetTradingProfitCalculator extends CtTypeConverters {

  def netTradingProfitCalculation(cp117: CP117,
                                  cp283: CP283): CP284 = {
    CP284(Some(cp117.value - cp283.orZero))
  }

  def netTradingProfitForProfitsChargeable(netTradingProfit: CP284): CP291 = {
    val result = netTradingProfit.value

    CP291(result)
  }
}
