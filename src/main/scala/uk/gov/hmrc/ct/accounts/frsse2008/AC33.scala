

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frsse2008.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC33(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Profit or loss on ordinary activities before taxation") with CtOptionalInteger

object AC33 extends Calculated[AC33, Frsse2008AccountsBoxRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: Frsse2008AccountsBoxRetriever): AC33 = {
    calculatePreviousProfitOrLossBeforeTax(ac27 = boxRetriever.ac27(),
                                           ac29 = boxRetriever.ac29(),
                                           ac31 = boxRetriever.ac31())
  }
}
