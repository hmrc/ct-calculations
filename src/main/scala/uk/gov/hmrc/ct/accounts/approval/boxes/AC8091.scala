

package uk.gov.hmrc.ct.accounts.approval.boxes

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC8091(value: Option[Boolean]) extends CtBoxIdentifier(name = "Approve accounts approval statement") with CtOptionalBoolean with Input with ValidatableBox[AccountsBoxRetriever] {

  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors {
      validateBooleanAsTrue("AC8091", this)
    }
  }
}
