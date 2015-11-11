package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtOptionalInteger, CtBoxIdentifier, Calculated}

case class AC36(value: Option[Int]) extends CtBoxIdentifier(name = "Current Profit or loss for the financial year") with CtOptionalInteger

object AC36 extends Calculated[AC36, AccountsBoxRetriever] {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC36 = ???
}


