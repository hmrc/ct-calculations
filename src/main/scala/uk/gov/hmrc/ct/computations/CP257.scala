package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.SummaryCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP257(value: Option[Int]) extends CtBoxIdentifier("Trading losses brought forward (box 4)") with CtOptionalInteger

object CP257 extends Calculated[CP257, ComputationsBoxRetriever] with SummaryCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP257 =
    calculateTradingLossesBroughtForwardForSummary(fieldValueRetriever.retrieveCP238())
}
