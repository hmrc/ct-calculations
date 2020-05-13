

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP281(value: Option[Int]) extends CtBoxIdentifier("Losses brought forward")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {

    collectErrors(
      requiredErrorIf(value.isEmpty && boxRetriever.cpQ17.isTrue),
      cannotExistErrorIf(value.nonEmpty && !boxRetriever.cpQ17().orFalse),
      exceedsMax(value),
      belowMin(value, min = 1),
      sumBreakdownIncorrectErrors(boxRetriever)
    )
  }

  private def sumBreakdownIncorrectErrors(retriever: ComputationsBoxRetriever) = {
    val cp281a = retriever.cp281a()
    val cp281b = retriever.cp281b()
    failIf((cp281a.value.isDefined || cp281b.value.isDefined) && cp281a + cp281b != this.orZero) {
      Set(CtValidation(None, "error.CP281.breakdown.sum.incorrect"))
    }
  }
}

object CP281 {

  def apply(int: Int): CP281 = CP281(Some(int))
}
