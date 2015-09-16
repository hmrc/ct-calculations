package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.SummaryLossesArisingThisPeriodCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class B122(value: Option[Int]) extends CtBoxIdentifier("Trading losses arising") with CtOptionalInteger

object B122 extends Calculated[B122, ComputationsBoxRetriever] with SummaryLossesArisingThisPeriodCalculator{

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): B122 = {
    summaryTradingLossesArisingCalculation(cp118 = fieldValueRetriever.retrieveCP118())
  }
}
