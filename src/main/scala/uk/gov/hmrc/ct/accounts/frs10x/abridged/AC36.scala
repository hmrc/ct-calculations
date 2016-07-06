package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations.{ProfitOrLossBeforeTaxCalculator, ProfitOrLossFinancialYearCalculator}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}

case class AC36(value: Int) extends CtBoxIdentifier(name = "Profit or loss for financial year (current PoA)") with CtInteger

object AC36 extends Calculated[AC36, Frs10xAccountsBoxRetriever] with ProfitOrLossFinancialYearCalculator {

  override def calculate(boxRetriever: Frs10xAccountsBoxRetriever): AC36 = {
    import boxRetriever._
    calculateAC36(retrieveAC32(), retrieveAC34())
  }
}