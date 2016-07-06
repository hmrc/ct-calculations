package uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations

import uk.gov.hmrc.ct.accounts.frs10x.abridged._

trait ProfitOrLossFinancialYearCalculator {

  def calculateAC36(aC32: AC32, aC34: AC34): AC36 = {
    AC36(aC32.value - aC34.orZero)
  }

  def calculateAC37(aC33: AC33, aC35: AC35): AC37 = {
    AC37(aC33.value - aC35.orZero)
  }
}
