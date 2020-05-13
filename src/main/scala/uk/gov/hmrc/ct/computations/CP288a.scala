

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.Validators.TradingLossesValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP288a(value: Option[Int]) extends CtBoxIdentifier("Losses carried forward from before 01/04/2017")
  with CtOptionalInteger
  with Input
  with ValidatableBox[ComputationsBoxRetriever]
  with TradingLossesValidation {

  override def validate(retriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      requiredErrorIf(retriever.cp281a().isPositive && !hasValue),
      validateZeroOrPositiveInteger(this)
    )
  }
}

object CP288a {

  def apply(int: Int): CP288a = CP288a(Some(int))
}
