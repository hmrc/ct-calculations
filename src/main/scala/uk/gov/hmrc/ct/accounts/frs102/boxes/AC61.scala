/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.boxes

import uk.gov.hmrc.ct.accounts.frs102.calculations.NetCurrentAssetsLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC61(value: Option[Int]) extends CtBoxIdentifier(name = "Net current assets or liabilities (previous PoA)") with CtOptionalInteger

object AC61 extends Calculated[AC61, Frs102AccountsBoxRetriever] with NetCurrentAssetsLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs102AccountsBoxRetriever): AC61 = {
    import boxRetriever._
    calculatePreviousNetCurrentAssetsLiabilities(ac57(), ac139B(), ac59())
  }
}
