package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC37(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Profit or loss for the financial year") with CtOptionalInteger

object AC37 extends Calculated[AC37, AccountsBoxRetriever] {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC37 = ???
}
