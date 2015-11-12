package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{CtOptionalInteger, CtBoxIdentifier, Calculated}

case class AC41(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Net balance transferred to reserves") with CtOptionalInteger

object AC41 extends Calculated[AC41, AccountsBoxRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC41 = {
    calculatePreviousNetBalance(boxRetriever.retrieveAC37(), boxRetriever.retrieveAC39())
  }
}