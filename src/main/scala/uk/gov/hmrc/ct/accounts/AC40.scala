package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC40(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Net balance transferred to reserves") with CtOptionalInteger

object AC40 extends Calculated[AC40, AccountsBoxRetriever] {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC40 = ???
}