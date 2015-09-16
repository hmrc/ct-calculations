package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.NetTradingProfitCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP291(value: Option[Int]) extends CtBoxIdentifier(name = "Net trading profit") with CtOptionalInteger

object CP291 extends Calculated[CP291, ComputationsBoxRetriever] with NetTradingProfitCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP291 = {
    netTradingProfitForProfitsChargeable(fieldValueRetriever.retrieveCP284(), fieldValueRetriever.retrieveCP283())
  }

}
