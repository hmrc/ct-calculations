

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever
import uk.gov.hmrc.ct.ct600e.validations.ValidateRegisteredCharityNumber

case class E1001(value: Option[String]) extends CtBoxIdentifier("Registration number")
  with CtOptionalString with Input with ValidatableBox[CT600EBoxRetriever] with ValidateRegisteredCharityNumber {

  override def validate(boxRetriever: CT600EBoxRetriever) = validateRegisteredCharityNumber(this)
}
