/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs102.boxes._

trait BalanceSheetDebtorsCalculator extends DebitAwareCalculation  {

  def calculateAC140(ac134: AC134, ac136: AC136, ac138: AC138): AC140 = {
    sum(ac134, ac136, ac138)(AC140.apply)
  }

  def calculateAC141(ac135: AC135, ac137: AC137, ac139: AC139): AC141 = {
    sum(ac135, ac137, ac139)(AC141.apply)
  }

}
