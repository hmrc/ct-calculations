

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait LossesSetAgainstOtherProfitsCalculator extends CtTypeConverters {

  def calculateLossesSetAgainstProfits(cato01: CATO01, cp997: CP997Abstract, cp118: CP118, cpq19: CPQ19): CP998 = {
    CP998(
      cpq19.value match {
        case Some(true) => Some(cp118.value min (cato01 - cp997))
        case _ => None
      }
    )
  }
}
