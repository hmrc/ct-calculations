

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait TradingLossesCP286MaximumCalculator extends CtTypeConverters {

  def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp283: CP283, cp997: CP997Abstract, cp998: CP998): Int = {
    (cp117 + cato01 - cp283 - cp997 - cp998) max 0
  }
}
