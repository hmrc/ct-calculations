

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP78(value: Option[Int]) extends CtBoxIdentifier(name = "Written down value brought forward") with CtOptionalInteger with Input with SelfValidatableBox[ComputationsBoxRetriever,  Option[Int]] {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    collectErrors(
      validateZeroOrPositiveInteger()
    )
  }
}

object CP78 {

  def apply(value: Int): CP78 = CP78(Some(value))
}
