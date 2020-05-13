

package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever
import uk.gov.hmrc.ct.ct600e.validations.ValidateRegisteredCharityNumber

case class E10(value: Option[String]) extends CtBoxIdentifier("Charity Commission registration number, or OSCR number (if applicable)")
  with CtOptionalString with Input with ValidatableBox[CT600EBoxRetriever] with ValidateRegisteredCharityNumber {

  override def validate(boxRetriever: CT600EBoxRetriever) = validateRegisteredCharityNumber(this)

}
