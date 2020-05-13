

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalAssetsLessCurrentLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC63(value: Option[Int]) extends CtBoxIdentifier(name = "Total assets less current liabilities (previous PoA)") with CtOptionalInteger

object AC63 extends Calculated[AC63, Frs102AccountsBoxRetriever] with TotalAssetsLessCurrentLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC63 = {
    import boxRetriever._
    calculatePreviousTotalAssetsLessCurrentLiabilities(ac61(), ac49())
  }
}
