/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.TotalFixedAssetsCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC48(value: Option[Int]) extends CtBoxIdentifier(name = "Total fixed assets (current PoA)") with CtOptionalInteger

object AC48 extends Calculated[AC48, Frs102AccountsBoxRetriever] with TotalFixedAssetsCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC48 = {
    import boxRetriever._
    calculateCurrentTotalFixedAssets(ac42(), ac44())
  }
}
