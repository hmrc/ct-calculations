/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.box.CtTypeConverters

trait NetCurrentAssetsLiabilitiesCalculator extends CtTypeConverters {

  def calculateCurrentNetCurrentAssetsLiabilities(ac56: AC56, ac138B: AC138B, ac58: AC58): AC60 = {
    (ac56.value, ac138B.value, ac58.value) match {
      case (None, None, None) => AC60(None)
      case _ => AC60(Some(ac56 + ac138B - ac58))
    }
  }

  def calculatePreviousNetCurrentAssetsLiabilities(ac57: AC57, ac139B: AC139B, ac59: AC59): AC61 = {
    (ac57.value, ac139B.value, ac59.value) match {
      case (None, None, None) => AC61(None)
      case _ => AC61(Some(ac57 + ac139B - ac59))
    }
  }

}
