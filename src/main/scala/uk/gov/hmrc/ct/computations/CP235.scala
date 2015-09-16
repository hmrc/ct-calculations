package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.SummaryLossesArisingThisPeriodCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP235(value: Option[Int]) extends CtBoxIdentifier(name = "Losses arising in this period") with CtOptionalInteger

object CP235 extends Calculated[CP235, ComputationsBoxRetriever] with SummaryLossesArisingThisPeriodCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP235 = {
    summaryLossesArisingThisPeriodCalculation(cp118 = fieldValueRetriever.retrieveCP118())
  }

}
