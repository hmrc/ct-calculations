

package uk.gov.hmrc.ct.accounts.frsse2008

import uk.gov.hmrc.ct.accounts.frsse2008.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC37(value: Option[Int]) extends CtBoxIdentifier(name = "Previous Profit or loss for the financial year") with CtOptionalInteger

object AC37 extends Calculated[AC37, Frsse2008AccountsBoxRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: Frsse2008AccountsBoxRetriever): AC37 = {
    calculatePreviousProfitOtLossAfterTax(ac33 = boxRetriever.ac33(),
                                          ac35 = boxRetriever.ac35())
  }
}
