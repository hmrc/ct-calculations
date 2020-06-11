/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.accounts.{AC12, AC401, AC402}

trait GrossProfitAndLossCalculator  extends DebitAwareCalculation {

  def calculateAC24Full(ac12: AC12, ac401: AC401, ac14: AC14): AC24 = {
    sum(ac12, ac401, ac14)(AC24.apply)
  }

  def calculateAC24Abridged(ac16: AC16, ac401: AC401): AC24 = {
    sum(ac16, ac401)(AC24.apply)
  }

  def calculateAC25Full(ac13: AC13, ac402: AC402, ac15: AC15): AC25 = {
    sum(ac13, ac402, ac15)(AC25.apply)
  }
  def calculateAC25Abridged(ac17: AC17, ac402: AC402): AC25 = {
    sum(ac17, ac402)(AC25.apply)
  }
}
