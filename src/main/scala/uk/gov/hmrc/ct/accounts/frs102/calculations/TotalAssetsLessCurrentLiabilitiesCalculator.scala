/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.box.CtTypeConverters

trait TotalAssetsLessCurrentLiabilitiesCalculator extends CtTypeConverters {

  def calculateCurrentTotalAssetsLessCurrentLiabilities(ac60: AC60, ac48: AC48): AC62 = {
    (ac60.value, ac48.value) match {
      case (None, None) => AC62(None)
      case _ => AC62(Some(ac60 + ac48))
    }
  }

  def calculatePreviousTotalAssetsLessCurrentLiabilities(ac61: AC61, ac49: AC49): AC63 = {
    (ac61.value, ac49.value) match {
      case (None, None) => AC63(None)
      case _ => AC63(Some(ac61 + ac49))
    }
  }

}
