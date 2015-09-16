package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever


case class B40(value: Option[Boolean]) extends CtBoxIdentifier("A repayment is due for this return period")
  with CtOptionalBoolean with Input with ValidatableBox[CT600BoxRetriever] {

  def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = validateBooleanAsMandatory("B40", this)

}
