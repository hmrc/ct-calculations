package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.ProfitAndLossCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP44(value: Int) extends CtBoxIdentifier(name = "Profit or losses before tax") with CtInteger

object CP44 extends Calculated[CP44, ComputationsBoxRetriever] with ProfitAndLossCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP44 = {
    calculateGrossProfitOrLossBeforeTax(cp14 = fieldValueRetriever.retrieveCP14(),
                                        cp40 = fieldValueRetriever.retrieveCP40(),
                                        cp43 =fieldValueRetriever.retrieveCP43(),
                                        cp501 =fieldValueRetriever.retrieveCP501(),
                                        cp502 = fieldValueRetriever.retrieveCP502())
  }
}
