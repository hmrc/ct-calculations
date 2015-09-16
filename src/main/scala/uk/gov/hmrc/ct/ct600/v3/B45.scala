package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever


case class B45(value: Option[Boolean]) extends CtBoxIdentifier("Are you owed a repayment for an earlier period?")
  with CtOptionalBoolean with Input with ValidatableBox[CT600BoxRetriever] {

  def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = validateBooleanAsMandatory("B45", this)

}
