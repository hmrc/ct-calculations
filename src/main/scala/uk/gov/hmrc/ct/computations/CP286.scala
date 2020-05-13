

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.calculations.TradingLossesCP286MaximumCalculator
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP286(value: Option[Int]) extends CtBoxIdentifier(name = "Losses claimed from a later AP")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation
  with TradingLossesCP286MaximumCalculator {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(value.isEmpty && boxRetriever.cpQ18().isTrue),
      cannotExistErrorIf(value.nonEmpty && !boxRetriever.cpQ18().orFalse),
      exceedsMax(value, calculateMaximumCP286(
        boxRetriever.cp117(), boxRetriever.cato01(),
        boxRetriever.cp283(), boxRetriever.chooseCp997(), boxRetriever.cp998())),
      belowMin(value, 0),
      sumOfBreakDownError(boxRetriever)
    )
  }

  private def sumOfBreakDownError(retriever: ComputationsBoxRetriever) = {
    failIf(retriever.cp286a().orZero + retriever.cp286b().orZero > this.orZero) {
      Set(CtValidation(Some("CP286"), "error.CP286.breakdown.sum.incorrect"))
    }
  }
}
