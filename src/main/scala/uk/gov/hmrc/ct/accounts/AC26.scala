package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.accounts.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC26(value: Option[Int]) extends CtBoxIdentifier(name = "Current Operating profit or loss") with CtOptionalInteger

object AC26 extends Calculated[AC26, AccountsBoxRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: AccountsBoxRetriever): AC26 = {
    calculateCurrentOperatingProfitOrLoss(boxRetriever.retrieveAC16(),
                                          ac18 = boxRetriever.retrieveAC18(),
                                          ac20 = boxRetriever.retrieveAC20(),
                                          ac22 = boxRetriever.retrieveAC22())
  }
}
