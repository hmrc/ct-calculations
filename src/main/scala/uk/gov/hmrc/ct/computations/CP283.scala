package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.LossesBroughtForwardAgainstTradingProfitCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP283(value: Option[Int]) extends CtBoxIdentifier(name = "Losses brought forward used against trading profit") with CtOptionalInteger

object CP283 extends Calculated[CP283, ComputationsBoxRetriever] with LossesBroughtForwardAgainstTradingProfitCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP283 = {
    lossesBroughtForwardUsedAgainstTradingProfitCalculation(cpq17 = fieldValueRetriever.retrieveCPQ17(),
                                                            cp281 = fieldValueRetriever.retrieveCP281(),
                                                            cp282 = fieldValueRetriever.retrieveCP282())
  }

}
