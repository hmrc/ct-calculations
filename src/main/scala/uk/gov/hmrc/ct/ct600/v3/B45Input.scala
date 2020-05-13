

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.ct600.v3.retriever.AboutThisReturnBoxRetriever


case class B45Input(value: Option[Boolean]) extends CtBoxIdentifier("Are you owed a repayment for an earlier period?")
  with CtOptionalBoolean with Input with ValidatableBox[AboutThisReturnBoxRetriever] {

  override def validate(boxRetriever: AboutThisReturnBoxRetriever): Set[CtValidation] = validateBooleanAsMandatory("B45Input", this)

}
