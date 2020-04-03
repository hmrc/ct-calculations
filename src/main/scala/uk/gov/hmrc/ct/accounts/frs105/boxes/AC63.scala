/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.calculations.TotalAssetsLessCurrentLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC63(value: Option[Int]) extends CtBoxIdentifier(name = "Total assets less current liabilities (previous PoA)") with CtOptionalInteger

object AC63 extends Calculated[AC63, Frs105AccountsBoxRetriever] with TotalAssetsLessCurrentLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs105AccountsBoxRetriever): AC63 = {
    import boxRetriever._
    calculatePreviousTotalAssetsLessCurrentLiabilities(ac61(), ac451(), ac461())
  }
}
