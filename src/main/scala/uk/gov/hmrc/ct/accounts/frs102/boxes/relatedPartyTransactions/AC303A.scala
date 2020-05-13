

package uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions

import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC303A(value: Option[Int]) extends CtBoxIdentifier(name = "Balance at end of POA")
  with CtOptionalInteger
  with Input
  with ValidatableBox[Frs102AccountsBoxRetriever]
  with Validators {

  override def validate(boxRetriever: Frs102AccountsBoxRetriever): Set[CtValidation] =
    collectErrors {
      validateMoney(value, min = 0)
    }
}
