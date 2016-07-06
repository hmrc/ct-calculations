package uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations

import uk.gov.hmrc.ct.accounts.frs10x.abridged._

trait ProfitOrLossBeforeTaxCalculator {

  def calculateAC32(aC26: AC26, aC28: AC28, aC30: AC30): AC32 = {
    AC32(aC26.value + aC28.orZero - aC30.orZero)
  }

  def calculateAC33(aC27: AC27, aC29: AC29, aC31: AC31): AC33 = {
    AC33(aC27.value + aC29.orZero - aC31.orZero)
  }
}
