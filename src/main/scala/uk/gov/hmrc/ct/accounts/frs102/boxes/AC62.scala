

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalAssetsLessCurrentLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC62(value: Option[Int]) extends CtBoxIdentifier(name = "Total assets less current liabilities (current PoA)") with CtOptionalInteger

object AC62 extends Calculated[AC62, Frs102AccountsBoxRetriever] with TotalAssetsLessCurrentLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC62 = {
    import boxRetriever._
    calculateCurrentTotalAssetsLessCurrentLiabilities(ac60(), ac48())
  }
}
