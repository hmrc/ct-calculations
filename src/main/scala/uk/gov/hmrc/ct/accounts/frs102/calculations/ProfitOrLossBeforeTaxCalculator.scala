/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.{AC401, AC402}
import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs102.boxes._

trait ProfitOrLossBeforeTaxCalculator extends DebitAwareCalculation {

  def calculateAC32(aC26: AC26, aC28: AC28, aC30: AC30): AC32 = {
    sum(aC26, aC28, aC30)(AC32.apply)
  }

  def calculateAC32OPW(aC26: AC26, aC28: AC28, aC30: AC30, ac401: AC401): AC32 = {
    sum(aC26, aC28, aC30, ac401)(AC32.apply)
  }

  def calculateAC33(aC27: AC27, aC29: AC29, aC31: AC31): AC33 = {
    sum(aC27, aC29, aC31)(AC33.apply)
  }

  def calculateAC33OPW(aC27: AC27, aC29: AC29, aC31: AC31, ac402: AC402): AC33 = {
    sum(aC27, aC29, aC31, ac402)(AC33.apply)
  }
}
