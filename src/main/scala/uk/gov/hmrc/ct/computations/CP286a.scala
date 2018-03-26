package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandRateValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP286a (value: Option[Int])
  extends CtBoxIdentifier("Later to current stuff...")
    with CtOptionalInteger
    with Input
    with ValidatableBox[ComputationsBoxRetriever] with NorthernIrelandRateValidation with TradingLossesValidation{

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(value.isEmpty && boxRetriever.cpQ18.isTrue),
      cannotExistErrorIf(value.nonEmpty && !boxRetriever.cpQ18().orFalse),
      requiredErrorIf(
          mayHaveNirLosses(boxRetriever) &&
          !hasValue),
      validateIntegerRange("CP286", this, 0, boxRetriever.cp286().orZero),
      sumOfBreakDownError(boxRetriever)
    )
  }

  private def sumOfBreakDownError(retriever: ComputationsBoxRetriever) = {
    failIf(this.orZero + retriever.cp286b().orZero > retriever.cp286().orZero){
      Set(CtValidation(None, "error.exceeds.tradingProfit.error"))

    }
  }

}

object CP286a {

  def apply(int: Int): CP286a = CP286a(Some(int))
}
