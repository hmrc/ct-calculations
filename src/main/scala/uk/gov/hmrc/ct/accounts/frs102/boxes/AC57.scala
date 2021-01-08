/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalCurrentAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC57(value: Option[Int]) extends CtBoxIdentifier(name = "Total current assets (previous PoA)") with CtOptionalInteger

object AC57 extends Calculated[AC57, Frs102AccountsBoxRetriever] with TotalCurrentAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC57 = {
    import boxRetriever._
    calculatePreviousTotalCurrentAssets(ac51(), ac53(), ac55())
  }
}
