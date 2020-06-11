/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.AC401
import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs102.boxes._

trait OperatingProfitOrLossCalculator extends DebitAwareCalculation {

  def calculateAC26(ac24: AC24, ac18: AC18, ac20: AC20, ac22: AC22 = AC22(None)): AC26 = {
    sum(ac24, ac18, ac20, ac22)(AC26.apply)
  }

  def calculateAC26OPW(ac16: AC16, ac18: AC18, ac20: AC20, ac401: AC401, ac22: AC22 = AC22(None)): AC26 = {
    sum(ac16, ac18, ac20, ac22, ac401)(AC26.apply)
  }


  def calculateAC27(ac17: AC17, ac19: AC19, ac21: AC21, ac23: AC23 = AC23(None)): AC27 = {
    sum(ac17, ac19, ac21, ac23)(AC27.apply)
  }

  def calculateAC27OPW(ac16: AC16, ac18: AC18, ac20: AC20, ac401: AC401, ac22: AC22 = AC22(None)): AC26 = {
    sum(ac16, ac18, ac20, ac22, ac401)(AC26.apply)
  }
}
