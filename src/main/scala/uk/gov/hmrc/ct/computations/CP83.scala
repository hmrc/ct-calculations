

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP83(value: Option[Int]) extends CtBoxIdentifier(name = "Expenditure qualifying for annual investment allowance(AIA)")  with CtOptionalInteger with Input with SelfValidatableBox[ComputationsBoxRetriever, Option[Int]] {
  override def validate(boxRetriever: ComputationsBoxRetriever) = {
    collectErrors(
      cannotExistErrorIf(hasValue && boxRetriever.cpQ8().isTrue),
      validateZeroOrPositiveInteger()
    )
  }
}

object CP83 {

  def apply(value: Int): CP83 = CP83(Some(value))
}
