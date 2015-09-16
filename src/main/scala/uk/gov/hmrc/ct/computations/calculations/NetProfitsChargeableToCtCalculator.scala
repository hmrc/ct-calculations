package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP293, CP294, CP295, CP999}

trait NetProfitsChargeableToCtCalculator extends CtTypeConverters {

  def calculateNetProfitsChargeableToCt(totalProfitsBeforeDeductions: CP293,
                                        tradingLossesOfThisPeriodAndLaterPeriods: CP294,
                                        totalDonations: CP999):CP295 = {
    val result = totalProfitsBeforeDeductions - tradingLossesOfThisPeriodAndLaterPeriods - totalDonations
    CP295(result max 0)
  }
}
