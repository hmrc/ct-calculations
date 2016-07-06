package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations.ProfitOrLossBeforeTaxCalculator
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}

case class AC32(value: Int) extends CtBoxIdentifier(name = "Profit or loss before tax (current PoA)") with CtInteger

object AC32 extends Calculated[AC32, Frs10xAccountsBoxRetriever] with ProfitOrLossBeforeTaxCalculator {

  override def calculate(boxRetriever: Frs10xAccountsBoxRetriever): AC32 = {
    import boxRetriever._
    calculateAC32(retrieveAC26(), retrieveAC28(), retrieveAC30())
  }
}