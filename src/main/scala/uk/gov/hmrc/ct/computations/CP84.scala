

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP84(value: Option[Int]) extends CtBoxIdentifier(name = "Disposal proceeds")  with CtOptionalInteger with Input with SelfValidatableBox[ComputationsBoxRetriever, Option[Int]] {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    collectErrors(
      validateZeroOrPositiveInteger()
    )
  }
}

object CP84 {

  def apply(value: Int): CP84 = CP84(Some(value))
}
