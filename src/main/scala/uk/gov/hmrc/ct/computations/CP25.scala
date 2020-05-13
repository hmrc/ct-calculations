

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP25(value: Option[Int]) extends CtBoxIdentifier(name = "Advertising and promotions") with CtOptionalInteger with Input
  with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    validateZeroOrPositiveInteger(this)
  }
}

object CP25 {

  def apply(int: Int): CP25 = CP25(Some(int))

}
