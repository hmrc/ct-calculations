package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.LossesBroughtForwardAgainstTradingProfitCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP290(value: Option[Int]) extends CtBoxIdentifier(name = "Adjusted Trading Or Loss") with CtOptionalInteger

object CP290 extends Calculated[CP290, ComputationsBoxRetriever] with LossesBroughtForwardAgainstTradingProfitCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP290 = {
    lossesBroughtForwardUsedAgainstTradingProfitForProfitsChargeable(fieldValueRetriever.retrieveCP283())
  }

}
