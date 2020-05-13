

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP287(value: Option[Int]) extends CtBoxIdentifier(name = "Amount of loss carried back to earlier periods")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {


  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    import boxRetriever._
    val max = cp118().value - cp998().orZero

    collectErrors(
      requiredErrorIf(value.isEmpty && boxRetriever.cpQ20.isTrue),
      cannotExistErrorIf({ value.nonEmpty && !boxRetriever.cpQ20().orFalse }),
      exceedsMax(value, max),
      belowMin(value, 1)
    )
  }
}

object CP287 {
  def apply(int: Int): CP287 = CP287(Some(int))
}
