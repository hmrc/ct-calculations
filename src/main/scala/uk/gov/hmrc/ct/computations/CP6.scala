package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.calculations.{ApportionedProfitAndLossCalculator, ApportionmentProfitOrLossResult}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP6(value: ApportionmentProfitOrLossResult) extends CtBoxIdentifier(name = "Apportioned Profit or Loss")

object CP6 extends Calculated[CP6, ComputationsBoxRetriever] with ApportionedProfitAndLossCalculator {
  override def calculate(fieldValueRetriever: ComputationsBoxRetriever): CP6 = {
//    apportionmentProfitOrLossCalculation()
    ???
  }
}
