/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.accounts.{AC12, AC401, AC402, AC403, AC404}

trait GrossProfitAndLossCalculator  extends DebitAwareCalculation {

  def calculateAC24Full(ac12: AC12, ac401: AC401, ac403: AC403, ac14: AC14): AC24 = {
    sum(ac12, ac401, ac403, ac14)(AC24.apply)
  }

  def calculateAC24Abridged(ac16: AC16, ac401: AC401, ac403: AC403): AC24 = {
    sum(ac16, ac401, ac403)(AC24.apply)
  }

  def calculateAC17Full(ac13: AC13, ac402: AC402, ac404: AC404, ac15: AC15): AC17= {
    sum(ac13, ac402, ac404, ac15)(AC17.apply)
  }
  def calculateAC17Abridged(ac17: AC17, ac402: AC402, ac404: AC404, ac15: AC15): AC17= {
    sum(ac17, ac402, ac404, ac15)(AC17.apply)
  }
}
