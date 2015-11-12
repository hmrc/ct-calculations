package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtOptionalInteger, CtBoxIdentifier}

case class AC16(value: Option[Int]) extends CtBoxIdentifier(name = "Current Gross profit or loss") with CtOptionalInteger

object AC16 extends Calculated[AC16, AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC16 = {
    calculateCurrentGrossProfitOrLoss(ac12 = boxRetriever.retrieveAC12(),
                                      ac14 = boxRetriever.retrieveAC14(),
                                      statutoryAccountsFiling = boxRetriever.retrieveStatutoryAccountsFiling())
  }
}