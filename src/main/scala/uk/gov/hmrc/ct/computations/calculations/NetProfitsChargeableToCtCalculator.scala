

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import uk.gov.hmrc.ct.computations.losses._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

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
