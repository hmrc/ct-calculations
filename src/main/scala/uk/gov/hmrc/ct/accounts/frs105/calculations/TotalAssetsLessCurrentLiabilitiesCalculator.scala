/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs105.boxes._

trait TotalAssetsLessCurrentLiabilitiesCalculator extends DebitAwareCalculation {

  def calculateCurrentTotalAssetsLessCurrentLiabilities(ac60: AC60, ac450: AC450, ac460: AC460): AC62 = {
    sum(ac60, ac450, ac460)(AC62.apply)
  }

  def calculatePreviousTotalAssetsLessCurrentLiabilities(ac61: AC61, ac451: AC451, ac461: AC461): AC63 = {
    sum(ac61, ac451, ac461)(AC63.apply)
  }

}
