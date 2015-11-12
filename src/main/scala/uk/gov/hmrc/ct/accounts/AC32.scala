package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC32(value: Option[Int]) extends CtBoxIdentifier(name = "Current Profit or loss on ordinary activities before taxation") with CtOptionalInteger

object AC32 extends Calculated[AC32, AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC32 = {
    calculateCurrentProfitOrLossBeforeTax(ac26 = boxRetriever.retrieveAC26(),
                                          ac28 = boxRetriever.retrieveAC28(),
                                          ac30 = boxRetriever.retrieveAC30())
  }
}


