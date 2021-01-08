/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.calculations.NetCurrentAssetsLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box._

case class AC60(value: Option[Int]) extends CtBoxIdentifier(name = "Net current assets or liabilities (current PoA)") with CtOptionalInteger

object AC60 extends Calculated[AC60, Frs105AccountsBoxRetriever] with NetCurrentAssetsLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs105AccountsBoxRetriever): AC60 = {
    import boxRetriever._
    calculateCurrentNetCurrentAssetsLiabilities(ac455(), ac465(), ac58())
  }
}
