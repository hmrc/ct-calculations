

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalCurrentAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC56(value: Option[Int]) extends CtBoxIdentifier(name = "Total current assets (current PoA)") with CtOptionalInteger

object AC56 extends Calculated[AC56, Frs102AccountsBoxRetriever] with TotalCurrentAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC56 = {
    import boxRetriever._
    calculateCurrentTotalCurrentAssets(ac50(), ac52(), ac54())
  }
}
