

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever

case class B42a(value: Option[Boolean]) extends CtBoxIdentifier("Small Companies Rate claimed") with CtOptionalBoolean with Input with ValidatableBox[CT600BoxRetriever] {
  override def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = validateAsMandatory(this)
}
