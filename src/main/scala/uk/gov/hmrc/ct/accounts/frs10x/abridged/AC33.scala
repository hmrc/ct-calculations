package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations.ProfitOrLossBeforeTaxCalculator
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}

case class AC33(value: Int) extends CtBoxIdentifier(name = "Profit or loss before tax (previous PoA)") with CtInteger

object AC33 extends Calculated[AC33, Frs10xAccountsBoxRetriever] with ProfitOrLossBeforeTaxCalculator {

  override def calculate(boxRetriever: Frs10xAccountsBoxRetriever): AC33 = {
    import boxRetriever._
    calculateAC33(retrieveAC27(), retrieveAC29(), retrieveAC31())
  }
}