

package uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC7801(value: Option[Boolean]) extends CtBoxIdentifier(name = "is incoming (vs outgoing) transaction")
  with CtOptionalBoolean
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateAsMandatory(this)
    )
  }
}
