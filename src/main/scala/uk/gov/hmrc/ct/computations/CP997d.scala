

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandRateValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP997d(value: Option[Int])
  extends CtBoxIdentifier("Main stream Losses from previous AP after 01/04/2017 set against non trading profits this AP")
    with CtOptionalInteger
    with Input
    with ValidatableBox[ComputationsBoxRetriever]
    with NorthernIrelandRateValidation {

  override def validate(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(retriever.cp281b().isPositive &&
        mayHaveNirLosses(retriever) &&
        retriever.cato01().value > 0 &&
        !hasValue),
      validateZeroOrPositiveInteger(this),
      exceedsNonTradingProfitErrors(retriever),
      exceedsNonTradingProfitErrorsAfterCPQ19(retriever)
    )
  }

  private def exceedsNonTradingProfitErrors(retriever: ComputationsBoxRetriever) = {
    failIf(this.value.isDefined &&
      retriever.cato01() < this.orZero + CP997e.calculate(retriever)) {
      Set(CtValidation(Some("CP997d"), "error.CP997.exceeds.nonTradingProfit"))
    }
  }

  private def exceedsNonTradingProfitErrorsAfterCPQ19(retriever: ComputationsBoxRetriever) = {
    failIf(retriever.cpQ19().isTrue && this.value.isDefined &&
      retriever.cp44().value < this.orZero + CP997e.calculate(retriever)) {
      Set(CtValidation(Some("CP997d"), "error.CP997.exceeds.nonTradingProfit.CPQ19"))
    }
  }


}
