

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.AdjustedTradingProfitCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP289(value: Option[Int]) extends CtBoxIdentifier(name = "Adjusted trading profits") with CtOptionalInteger

object CP289 extends Calculated[CP289, ComputationsBoxRetriever] with AdjustedTradingProfitCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP289 = {
    adjustedTradingProfitCalculation(cp117 = fieldValueRetriever.cp117())
  }
}
