

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.RepaymentsBoxRetriever

case class REPAYMENTSQ1(value: Option[Boolean]) extends CtBoxIdentifier("Send Repayment in all cases?")
with CtOptionalBoolean with Input with ValidatableBox[RepaymentsBoxRetriever] {

  override def validate(boxRetriever: RepaymentsBoxRetriever): Set[CtValidation] = validateAsMandatory(this)
}
