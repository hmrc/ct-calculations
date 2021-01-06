/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.box.CtTypeConverters

trait TotalNetAssetsLiabilitiesCalculator extends CtTypeConverters {

  def calculateCurrentTotalNetAssetsLiabilities(ac62: AC62, ac64: AC64, ac66: AC66, ac150B: AC150B): AC68 = {
    (ac62.value, ac64.value, ac66.value, ac150B.value) match {
      case (None, None, None, None) => AC68(None)
      case _ => AC68(Some(ac62 - ac64 - ac66 - ac150B))
    }
  }

  def calculatePreviousTotalNetAssetsLiabilities(ac63: AC63, ac65: AC65, ac67: AC67, ac151B: AC151B): AC69 = {
    (ac63.value, ac65.value, ac67.value, ac151B.value) match {
      case (None, None, None, None) => AC69(None)
      case _ => AC69(Some(ac63 - ac65 - ac67 - ac151B))
    }
  }
}
