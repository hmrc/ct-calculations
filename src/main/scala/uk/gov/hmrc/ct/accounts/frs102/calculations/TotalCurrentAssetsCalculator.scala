/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.box.CtTypeConverters

trait TotalCurrentAssetsCalculator extends CtTypeConverters {

  def calculateCurrentTotalCurrentAssets(ac50: AC50, ac52: AC52, ac54: AC54): AC56 = {
    (ac50.value, ac52.value, ac54.value) match {
      case (None, None, None) => AC56(None)
      case _ => AC56(Some(ac50 + ac52 + ac54))
    }
  }

  def calculatePreviousTotalCurrentAssets(ac51: AC51, ac53: AC53, ac55: AC55): AC57 = {
    (ac51.value, ac53.value, ac55.value) match {
      case (None, None, None) => AC57(None)
      case _ => AC57(Some(ac51 + ac53 + ac55))
    }
  }

}
