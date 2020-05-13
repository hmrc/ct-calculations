

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP301(value: Option[Int]) extends CtBoxIdentifier(name = "Qualifying charitable donations UK")
  with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      cannotExistErrorIf(hasValue && boxRetriever.cpQ21().isFalse),
      requiredErrorIf(!hasValue && boxRetriever.cpQ21().isTrue),
      validateZeroOrPositiveInteger(this)
    )
  }
}

object CP301 {

  def apply(int: Int): CP301 = CP301(Some(int))



}
