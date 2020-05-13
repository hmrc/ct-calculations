

package uk.gov.hmrc.ct.accounts.frsse2008.micro

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC425(value: Option[Int]) extends CtBoxIdentifier(name = "Current Other charges")
                                     with CtOptionalInteger with Input
                                     with SelfValidatableBox[AccountsBoxRetriever, Option[Int]] {
  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    validateMoney(value)
  }
}
