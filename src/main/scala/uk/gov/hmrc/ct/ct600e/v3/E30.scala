

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever
import uk.gov.hmrc.ct.ct600e.validations.ValidateDeclarationNameOrStatus

case class E30(value: Option[String]) extends CtBoxIdentifier("Claimer's name") with CtOptionalString with Input
  with ValidatableBox[CT600EBoxRetriever] with ValidateDeclarationNameOrStatus[CT600EBoxRetriever] {
  override def validate(boxRetriever: CT600EBoxRetriever): Set[CtValidation] = validateDeclarationNameOrStatus("E30", this)
}
