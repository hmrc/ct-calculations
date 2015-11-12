package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC33(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Profit or loss on ordinary activities before taxation") with CtOptionalInteger

object AC33 extends Calculated[AC33, AccountsBoxRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC33 = {
    calculatePreviousProfitOrLossBeforeTax(ac27 = boxRetriever.retrieveAC27(),
                                           ac29 = boxRetriever.retrieveAC29(),
                                           ac31 = boxRetriever.retrieveAC31())
  }
}


