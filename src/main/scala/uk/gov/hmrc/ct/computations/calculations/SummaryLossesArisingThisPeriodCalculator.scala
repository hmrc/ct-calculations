package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.computations.{CP118, CP235}
import uk.gov.hmrc.ct.ct600.v2.B122

trait SummaryLossesArisingThisPeriodCalculator {

  def summaryLossesArisingThisPeriodCalculation(cp118: CP118): CP235 = {
    CP235(
      cp118.value match {
        case t if t > 0 => Some(t)
        case _ => None
      })
  }

  def summaryTradingLossesArisingCalculation(cp118: CP118): B122 = {
    B122(
      cp118.value match {
      case t if t > 0 => Some(t)
      case _ => Some(0)
    })
  }
}
