

package uk.gov.hmrc.ct.computations.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.{CP501, CP502}

trait NonBankInterestSimilarIncomeReceivableCalculator extends CtTypeConverters {

  def nonBankInterestSimilarIncomeReceivableCalculation(cp501: CP501,
                                                        cp502: CP502): Option[Int] = {
    val result = (cp501.value, cp502.value) match {
      case (Some(gross), Some(income)) =>
        Some(gross + income)
      case _ =>
        None
    }
    result
  }
}
