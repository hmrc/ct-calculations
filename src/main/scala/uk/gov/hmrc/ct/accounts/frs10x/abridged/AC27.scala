package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations.OperatingProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtInteger}

case class AC27(value: Int) extends CtBoxIdentifier(name = "Operating profit or loss (current PoA)") with CtInteger

object AC27 extends Calculated[AC27, Frs10xAccountsBoxRetriever] with OperatingProfitOrLossCalculator {

  override def calculate(boxRetriever: Frs10xAccountsBoxRetriever): AC27 = {
    import boxRetriever._
    calculateAC27(retrieveAC17(), retrieveAC19(), retrieveAC21())
  }
}
