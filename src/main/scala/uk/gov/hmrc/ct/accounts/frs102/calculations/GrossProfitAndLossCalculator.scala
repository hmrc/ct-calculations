/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC13, AC24}
import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.accounts.frs105.boxes.AC25
import uk.gov.hmrc.ct.accounts.{AC12, AC401, AC402, AC403, AC404}

trait GrossProfitAndLossCalculator  extends DebitAwareCalculation {

  def calculateAC16(ac12: AC12, ac24: AC24, ac401: AC401, ac403: AC403, ac14: AC14): AC16 = {
    sum(ac12, ac24, ac401, ac403, ac14)(AC16.apply)
  }


  def calculateAC17(ac13: AC13, ac25: AC25, ac402: AC402, ac404: AC404, ac15: AC15): AC17 = {
    sum(ac13, ac25, ac402, ac404, ac15)(AC17.apply)
  }
}