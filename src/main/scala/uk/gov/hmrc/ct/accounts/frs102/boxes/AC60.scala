/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.NetCurrentAssetsLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC60(value: Option[Int]) extends CtBoxIdentifier(name = "Net current assets or liabilities (current PoA)") with CtOptionalInteger

object AC60 extends Calculated[AC60, Frs102AccountsBoxRetriever] with NetCurrentAssetsLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC60 = {
    import boxRetriever._
    calculateCurrentNetCurrentAssetsLiabilities(ac56(), ac138B(), ac58())
  }
}
