

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.calculations.NetCurrentAssetsLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC61(value: Option[Int]) extends CtBoxIdentifier(name = "Net current assets or liabilities (previous PoA)") with CtOptionalInteger

object AC61 extends Calculated[AC61, Frs105AccountsBoxRetriever] with NetCurrentAssetsLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs105AccountsBoxRetriever): AC61 = {
    import boxRetriever._
    calculatePreviousNetCurrentAssetsLiabilities(ac456(), ac466(), ac59())
  }
}
