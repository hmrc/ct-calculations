

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

case class CP501(value: Option[Int]) extends CtBoxIdentifier(name = "Gross income from property") with CtOptionalInteger with Input with ValidatableBox[ComputationsBoxRetriever] {

  override def validate(boxRetriever: ComputationsBoxRetriever): Set[CtValidation] = validateIntegerRange("CP501", this, 0, 5200)

}

object CP501 {

  def apply(int: Int): CP501 = CP501(Some(int))

}
