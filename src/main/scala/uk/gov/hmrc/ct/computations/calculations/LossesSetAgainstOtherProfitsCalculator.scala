package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.computations.{CP118, CP998, CPQ19}

trait LossesSetAgainstOtherProfitsCalculator {

  def calculateLossesSetAgainstProfits(cato01: CATO01, cp118: CP118, cpq19: CPQ19): CP998 = {
    CP998(
      cpq19.value match {
        case Some(true) => Some(cp118.value min cato01.value)
        case _ => None
      }
    )
  }
}
