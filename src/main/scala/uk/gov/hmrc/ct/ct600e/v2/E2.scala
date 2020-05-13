

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

case class E2(value: Option[Int]) extends CtBoxIdentifier("Total repayment") with CtOptionalInteger with Input with ValidatableBox[CT600EBoxRetriever] {

  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = validateZeroOrPositiveInteger(this)

}
