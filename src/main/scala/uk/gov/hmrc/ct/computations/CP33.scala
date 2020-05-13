

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP33(value: Option[Int]) extends CtBoxIdentifier(name = "Profit/Losses on disposal of assets") with CtOptionalInteger with Input
  with ValidatableBox[ComputationsBoxRetriever] {
  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = {
    Set()
  }
}

object CP33 {

  def apply(int: Int): CP33 = CP33(Some(int))

}
