package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.NetTradingProfitCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP284(value: Option[Int]) extends CtBoxIdentifier("Net trading profit") with CtOptionalInteger

object CP284 extends Calculated[CP284, ComputationsBoxRetriever] with NetTradingProfitCalculator  {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP284 =
  netTradingProfitCalculation(fieldValueRetriever.retrieveCP117(), fieldValueRetriever.retrieveCP283())

}
