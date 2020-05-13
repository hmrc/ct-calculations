

package uk.gov.hmrc.ct.ct600.calculations

import uk.gov.hmrc.ct.accounts.{AC3, AC4}
import uk.gov.hmrc.ct.utils.DateImplicits._

trait PeriodOfAccountsCalculator {
  
  def isLongPeriodOfAccounts(ac3: AC3, ac4: AC4): Boolean = {
    val oneCalendarYearFromStart = ac3.value.plusYears(1).minusDays(1)
    
    ac4.value > oneCalendarYearFromStart
  }

}
