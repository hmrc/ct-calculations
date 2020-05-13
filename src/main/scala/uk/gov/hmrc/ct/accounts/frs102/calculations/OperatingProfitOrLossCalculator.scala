

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs102.boxes._

trait OperatingProfitOrLossCalculator extends DebitAwareCalculation {

  def calculateAC26(ac16: AC16, ac18: AC18, ac20: AC20, ac22: AC22 = AC22(None)): AC26 = {
    sum(ac16, ac18, ac20, ac22)(AC26.apply)
  }

  def calculateAC27(ac17: AC17, ac19: AC19, ac21: AC21, ac23: AC23 = AC23(None)): AC27 = {
    sum(ac17, ac19, ac21, ac23)(AC27.apply)
  }
}
