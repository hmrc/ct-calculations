package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC26(value: Option[Int]) extends CtBoxIdentifier(name = "Current Operating profit or loss") with CtOptionalInteger

object AC26 extends Calculated[AC26, AccountsBoxRetriever] {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC26 = ???
}
