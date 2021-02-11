/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait NetProfitsChargeableToCtCalculator extends CtTypeConverters {

  def calculateNetProfitsChargeableToCt(totalProfitsBeforeDeductions: CP293,
                                        tradingLossesOfThisPeriodAndLaterPeriods: CP294,
                                        tradingLossesFromEarlierPeriodsUsedAgainstNonTradingProfit: CP997Abstract,
                                        totalDonations: CP999): CP295 = {
    val cp997 = tradingLossesFromEarlierPeriodsUsedAgainstNonTradingProfit.value.getOrElse(0)
    val result = totalProfitsBeforeDeductions - tradingLossesOfThisPeriodAndLaterPeriods - cp997 - totalDonations

    CP295(result max 0)
  }
}
