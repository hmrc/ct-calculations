

package uk.gov.hmrc.ct.accounts.approval.boxes

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.ValidatableBox._
import uk.gov.hmrc.ct.box._

case class AC199A(value: String) extends CtBoxIdentifier(name = "Approve accounts approver") with CtString with Input with ValidatableBox[AccountsBoxRetriever] {

  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    validateStringMaxLength("AC199A", this.value, StandardCohoNameFieldLimit) ++ validateCohoNameField("AC199A", this)
  }
}
