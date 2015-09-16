package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}
import uk.gov.hmrc.ct.computations.calculations.NonTradeIncomeCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CATO01(value: Int) extends CtBoxIdentifier(name = "VarA - Non trade income") with CtInteger

object CATO01 extends Calculated[CATO01, ComputationsBoxRetriever] with NonTradeIncomeCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CATO01 = {
    nonTradeIncomeCalculation(cp43 = fieldValueRetriever.retrieveCP43(),
                              cp502 = fieldValueRetriever.retrieveCP502(),
                              cp509 = fieldValueRetriever.retrieveCP509(),
                              cp510 = fieldValueRetriever.retrieveCP510())
  }
}

