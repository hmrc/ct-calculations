package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations._

trait TotalProfitsBeforeDeductionsCalculator extends CtTypeConverters {

  def computeTotalProfitsBeforeDeductionsAndReliefs(cp284: CP284,
                                                    cp58: CP58,
                                                    cp511: CP511,
                                                    cp502: CP502): CP293 = {
    val result = (cp284.value.getOrElse(0) max 0) + cp58.value + cp511.value + cp502.value.getOrElse(0)
    CP293(result max 0)
  }
}
