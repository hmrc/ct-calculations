

package uk.gov.hmrc.ct.accounts.frs102.calculations

import uk.gov.hmrc.ct.accounts.calculations.DebitAwareCalculation
import uk.gov.hmrc.ct.accounts.frs102.boxes._

trait TotalShareholdersFundsCalculator extends DebitAwareCalculation {

  def calculateCurrentTotalShareholdersFunds(ac70: AC70, ac76: AC76, ac74: AC74): AC80 = {
    sum(ac70, ac74, ac76)(AC80.apply)
  }

  def calculatePreviousTotalShareholdersFunds(ac71: AC71, ac77: AC77, ac75: AC75): AC81 = {
    sum(ac71, ac75, ac77)(AC81.apply)
  }
}
