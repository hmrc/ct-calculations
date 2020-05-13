

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP24(value: Option[Int]) extends CtBoxIdentifier(name = "Repairs, renewals and maintenance") with CtOptionalInteger with Input
  with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    validateZeroOrPositiveInteger(this)
  }
}

object CP24 {

  def apply(int: Int): CP24 = CP24(Some(int))

}
