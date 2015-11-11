package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtOptionalInteger, CtBoxIdentifier}

case class AC27(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Operating profit or loss") with CtOptionalInteger

object AC27 extends Calculated[AC27, AccountsBoxRetriever] {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC27 = ???
}
