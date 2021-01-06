/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs105.calculations

import uk.gov.hmrc.ct.accounts._
import uk.gov.hmrc.ct.accounts.frs105.boxes._
import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation

trait ProfitOrLossFinancialYearCalculator extends DebitAwareCalculation{

  def calculateAC435(aC12: AC12, aC24: AC24, aC405: AC405, aC410: AC410, aC415: AC415, aC420: AC420, aC425: AC425, aC34: AC34, ac401:AC401, ac403: AC403): AC435 = {
    sum(aC12, aC24, aC405, aC410, aC415, aC420, aC425, aC34, ac401, ac403)(AC435.apply)
  }

  def calculateAC436(aC13: AC13, aC25: AC25, aC406: AC406, aC411: AC411, aC416: AC416, aC421: AC421, aC426: AC426, aC35: AC35, aC402: AC402, aC404:AC404): AC436 = {
    sum(aC13, aC25, aC406, aC411, aC416, aC421, aC426, aC35, aC402, aC404)(AC436.apply)
  }
}
