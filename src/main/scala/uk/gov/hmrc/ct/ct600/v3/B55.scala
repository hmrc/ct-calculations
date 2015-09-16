package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever


case class B55(value: Option[Boolean]) extends CtBoxIdentifier("This return contains estimated figures")
  with CtOptionalBoolean with Input with ValidatableBox[CT600BoxRetriever] {

  def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = validateBooleanAsMandatory("B55", this)

}

