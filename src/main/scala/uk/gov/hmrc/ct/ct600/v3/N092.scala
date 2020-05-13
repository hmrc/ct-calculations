

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600DeclarationBoxRetriever


case class N092(value: Option[Boolean]) extends CtBoxIdentifier("Did you accept the declaration?")
  with CtOptionalBoolean with Input with ValidatableBox[CT600DeclarationBoxRetriever] {

  override def validate(boxRetriever: CT600DeclarationBoxRetriever): Set[CtValidation] = validateBooleanAsMandatory("N092", this)

}
