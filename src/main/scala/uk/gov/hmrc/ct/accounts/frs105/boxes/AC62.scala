/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.boxes

import uk.gov.hmrc.ct.accounts.frs105.calculations.TotalAssetsLessCurrentLiabilitiesCalculator
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.{Calculated, CtBoxIdentifier, CtOptionalInteger}

case class AC62(value: Option[Int]) extends CtBoxIdentifier(name = "Total assets less current liabilities (current PoA)") with CtOptionalInteger

object AC62 extends Calculated[AC62, Frs105AccountsBoxRetriever] with TotalAssetsLessCurrentLiabilitiesCalculator {

  override def calculate(boxRetriever: Frs105AccountsBoxRetriever): AC62 = {
    import boxRetriever._
    calculateCurrentTotalAssetsLessCurrentLiabilities(ac60(), ac450(), ac460())
  }
}
