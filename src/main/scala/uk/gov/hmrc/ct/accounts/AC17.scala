package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC17(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Gross profit or loss") with CtOptionalInteger

object AC17 extends Calculated[AC17, AccountsBoxRetriever] {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC17 = ???
}