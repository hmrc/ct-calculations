

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.AboutThisReturnBoxRetriever

case class B65(value: Option[Boolean]) extends CtBoxIdentifier("Notice of disclosable avoidance schemes") with CtOptionalBoolean with Input with ValidatableBox[AboutThisReturnBoxRetriever] {
  override def validate(boxRetriever: AboutThisReturnBoxRetriever): Set[CtValidation] = validateAsMandatory(this)
}
