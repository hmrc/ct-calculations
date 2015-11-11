package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtOptionalInteger, CtBoxIdentifier, Calculated}

case class AC33(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Profit or loss on ordinary activities before taxation") with CtOptionalInteger

object AC33 extends Calculated[AC33, AccountsBoxRetriever] {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC33 = ???
}


