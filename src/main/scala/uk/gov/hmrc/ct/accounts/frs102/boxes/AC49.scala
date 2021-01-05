/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalFixedAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC49(value: Option[Int]) extends CtBoxIdentifier(name = "Total fixed assets (previous PoA)") with CtOptionalInteger

object AC49 extends Calculated[AC49, Frs102AccountsBoxRetriever] with TotalFixedAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC49 = {
    import boxRetriever._
    calculatePreviousTotalFixedAssets(ac43(), ac45())
  }
}
