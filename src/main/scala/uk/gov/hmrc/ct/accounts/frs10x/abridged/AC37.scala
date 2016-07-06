package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations.ProfitOrLossFinancialYearCalculator
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}

case class AC37(value: Int) extends CtBoxIdentifier(name = "Profit or loss for financial year (current PoA)") with CtInteger

object AC37 extends Calculated[AC37, Frs10xAccountsBoxRetriever] with ProfitOrLossFinancialYearCalculator {

  override def calculate(boxRetriever: Frs10xAccountsBoxRetriever): AC37 = {
    import boxRetriever._
    calculateAC37(retrieveAC33(), retrieveAC35())
  }
}