

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait TotalProfitsBeforeDeductionsCalculator extends CtTypeConverters {

  def computeTotalProfitsBeforeDeductionsAndReliefs(cp284: CP284,
                                                    cp58: CP58,
                                                    cp511: CP511,
                                                    cp502: CP502): CP293 = {
    val result = (cp284.orZero max 0) + cp58.value + cp511.value + cp502.orZero
    CP293(result max 0)
  }
}
