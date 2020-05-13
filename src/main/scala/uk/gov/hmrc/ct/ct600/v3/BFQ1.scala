

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever


case class BFQ1(value: Option[Boolean]) extends CtBoxIdentifier("Did you receive any franked investment income?")
  with CtOptionalBoolean with Input with ValidatableBox[CT600BoxRetriever] {

  override def validate(boxRetriever: CT600BoxRetriever): Set[CtValidation] = validateBooleanAsMandatory("BFQ1", this)

}
