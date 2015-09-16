package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger, MustBeZeroOrPositive}
import uk.gov.hmrc.ct.computations.calculations.AdjustedTradingProfitOrLossCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP118(value: Int) extends CtBoxIdentifier(name = "Adjusted Trading Loss") with CtInteger with MustBeZeroOrPositive

object CP118 extends Calculated[CP118, ComputationsBoxRetriever] with AdjustedTradingProfitOrLossCalculator {

  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP118 = {
    calculateAdjustedTradingLoss(cp44 = fieldValueRetriever.retrieveCP44(),
                                 cp54 = fieldValueRetriever.retrieveCP54(),
                                 cp59 = fieldValueRetriever.retrieveCP59(),
                                 cp186 = fieldValueRetriever.retrieveCP186(),
                                 cp91 = fieldValueRetriever.retrieveCP91())
  }

}