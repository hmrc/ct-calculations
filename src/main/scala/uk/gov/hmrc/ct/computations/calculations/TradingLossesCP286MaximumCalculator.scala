package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.computations.{CP117, CP281, CP998}

trait TradingLossesCP286MaximumCalculator {

  def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp998: CP998, cp281: CP281): Int = {

    (cp117.value, cato01.value, cp281.value, cp998.value) match {
      case (0, ntp, _, Some(lossesCurrentToCurrent)) => (ntp - lossesCurrentToCurrent) max 0
      case (0, ntp, _, _) => ntp
      case (tp, ntp, Some(lossesPreviousToCurrent), _) if lossesPreviousToCurrent > tp => ntp
      case (tp, ntp, Some(lossesPreviousToCurrent), _) => tp + ntp - lossesPreviousToCurrent
      case _ => 0
    }
  }
}
