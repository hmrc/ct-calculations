package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC435(value: Option[Int]) extends CtBoxIdentifier(name = "Current Profit or loss") with CtOptionalInteger

object AC435 extends Calculated[AC435, AccountsBoxRetriever] {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC435 = ???
}
