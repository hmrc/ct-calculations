

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC14(value: Option[Int]) extends CtBoxIdentifier(name = "Cost of sales (current PoA)")
  with CtOptionalInteger
  with Input
  with ValidatableBox[FullAccountsBoxRetriever]
  with Validators
  with Debit {

  override def validate(boxRetriever: FullAccountsBoxRetriever): Set[CtValidation] = {
    collectErrors(
      validateMoney(value, min = 0)
    )
  }
}
