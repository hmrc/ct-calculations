/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs102.boxes._
import uk.gov.hmrc.ct.box.CtTypeConverters

trait BalanceSheetCreditorsCalculator extends DebitAwareCalculation {

  def calculateAC162(ac156: AC156, ac158: AC158, ac160: AC160): AC162 = {
    sum(ac156, ac158, ac160)(AC162.apply)
  }

  def calculateAC163(ac157: AC157, ac159: AC159, ac161: AC161): AC163 = {
    sum(ac157, ac159, ac161)(AC163.apply)
  }

}
