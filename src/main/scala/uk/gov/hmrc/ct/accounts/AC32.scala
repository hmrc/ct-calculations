package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC32(value: Option[Int]) extends CtBoxIdentifier(name = "Current Profit or loss on ordinary activities before taxation") with CtOptionalInteger

object AC32 extends Calculated[AC32, AccountsBoxRetriever] {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC32 = ???
}


