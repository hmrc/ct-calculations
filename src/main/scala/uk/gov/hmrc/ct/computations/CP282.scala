

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}
import uk.gov.hmrc.ct.computations.calculations.AdjustedTradingProfitForPeriodCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP282(value: Option[Int]) extends CtBoxIdentifier(name = "Adjusted Trading Profit for the Period") with CtOptionalInteger

object CP282 extends Calculated[CP282, ComputationsBoxRetriever] with AdjustedTradingProfitForPeriodCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP282 = {
    adjustedTradingProfitForPeriodCalculation(cp117 = fieldValueRetriever.cp117(),
                                              cpq17 = fieldValueRetriever.cpQ17())
  }
}
