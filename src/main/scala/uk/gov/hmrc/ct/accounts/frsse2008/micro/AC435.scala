

package uk.gov.hmrc.ct.accounts.frsse2008.micro

import uk.gov.hmrc.ct.accounts.frsse2008.calculations.ProfitOrLossCalculator
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class AC435(value: Option[Int]) extends CtBoxIdentifier(name = "Current Profit or loss") with CtOptionalInteger

object AC435 extends Calculated[AC435, Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever] with ProfitOrLossCalculator {
  override def calculate(boxRetriever: Frsse2008AccountsBoxRetriever with FilingAttributesBoxValueRetriever): AC435 = {
    calculateCurrentProfitOrLoss(ac12 = boxRetriever.ac12(), ac405 = boxRetriever.ac405(),
                                 ac410 = boxRetriever.ac410(), ac415 = boxRetriever.ac415(),
                                 ac420 = boxRetriever.ac420(), ac425 = boxRetriever.ac425(),
                                 ac34 = boxRetriever.ac34(), boxRetriever.microEntityFiling())
  }
}
