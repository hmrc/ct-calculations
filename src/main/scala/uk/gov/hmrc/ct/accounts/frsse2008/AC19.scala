

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC19(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Distribution costs")
                                    with CtOptionalInteger with Input
                                    with SelfValidatableBox[AccountsBoxRetriever, Option[Int]] {
  override def validate(boxRetriever: AccountsBoxRetriever): Set[CtValidation] = {
    validateMoney(value)
  }
}
