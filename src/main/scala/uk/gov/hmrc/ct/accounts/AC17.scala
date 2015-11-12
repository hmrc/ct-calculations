package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC17(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Gross profit or loss") with CtOptionalInteger

object AC17 extends Calculated[AC17, AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC17 = {
    calculatePreviousGrossProfitOrLoss(ac13 = boxRetriever.retrieveAC13(),
                                       ac15 = boxRetriever.retrieveAC15(),
                                       statutoryAccountsFiling = boxRetriever.retrieveStatutoryAccountsFiling())
  }
}