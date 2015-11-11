package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtOptionalInteger, CtBoxIdentifier}

case class AC16(value: Option[Int]) extends CtBoxIdentifier(name = "Current Gross profit or loss") with CtOptionalInteger

object AC16 extends Calculated[AC16, AccountsBoxRetriever] {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC16 = ???
}