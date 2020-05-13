

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.TotalDeductionsCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP59(value: Int) extends CtBoxIdentifier(name = "Total deductions") with CtInteger

object CP59 extends Calculated[CP59, ComputationsBoxRetriever] with TotalDeductionsCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP59 = {
    totalDeductionsCalculation(cp58 = fieldValueRetriever.cp58(),
                               cp505 = fieldValueRetriever.cp505(),
                               cp509 = fieldValueRetriever.cp509(),
                               cp55 = fieldValueRetriever.cp55(),
                               cp57 = fieldValueRetriever.cp57())
  }
}
