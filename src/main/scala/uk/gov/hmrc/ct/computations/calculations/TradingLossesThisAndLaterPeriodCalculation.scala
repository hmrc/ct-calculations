/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.computations.{CP286, CP294, CP998}

trait TradingLossesThisAndLaterPeriodCalculation {

  def tradingLosses(cp286: CP286, cp998: CP998): CP294 = {
    CP294(cp286.orZero + cp998.orZero)
  }
}
