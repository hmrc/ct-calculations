package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC436(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Profit or loss") with CtOptionalInteger

object AC436 extends Calculated[AC436, AccountsBoxRetriever] {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC436 = ???
}