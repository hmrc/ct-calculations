package uk.gov.hmrc.ct.accounts.frs10x.abridged.calculations

import uk.gov.hmrc.ct.accounts.frs10x.abridged._

trait OperatingProfitOrLossCalculator {

  def calculateAC26(aC16: AC16, aC18: AC18, aC20: AC20): AC26 = {
    AC26(aC16.orZero - aC18.orZero - aC20.orZero)
  }

  def calculateAC27(aC17: AC17, aC19: AC19, aC21: AC21): AC27 = {
    AC27(aC17.orZero - aC19.orZero - aC21.orZero)
  }
}
