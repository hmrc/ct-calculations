

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.frs102.boxes._

trait ProfitOrLossFinancialYearCalculator {

  def calculateAC36(aC32: AC32, aC34: AC34): AC36 = {
    (aC32.value, aC34.value) match {
      case (None, None) => AC36(None)
      case _ => AC36(Some(aC32.orZero - aC34.orZero))
    }
  }

  def calculateAC37(aC33: AC33, aC35: AC35): AC37 = {
    (aC33.value, aC35.value) match {
      case (None, None) => AC37(None)
      case _ => AC37(Some(aC33.orZero - aC35.orZero))
    }
  }
}
