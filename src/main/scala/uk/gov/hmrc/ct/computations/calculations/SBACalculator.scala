package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.computations.CP2
import uk.gov.hmrc.ct.ct600.NumberRounding
import uk.gov.hmrc.ct.ct600.calculations.AccountingPeriodHelper

trait SBACalculator extends NumberRounding with AccountingPeriodHelper {

  val rate: BigDecimal

  def apportionedCostOfBuilding(cost: BigDecimal, daysIntTheYear: Int): BigDecimal = (cost * rate) / daysIntTheYear

  def getDaysBetweenEarliestWrittenContractAndEndDate(dateOfPurchase: LocalDate, apEndDate: CP2): Int = daysBetween(dateOfPurchase, apEndDate.value)

  def isEarliestWrittenContractAfterAPStart(contractDate: LocalDate, apStartDate: LocalDate): Boolean = contractDate.isAfter(apStartDate)

  /*This is the 2%*/
  def getAmountClaimableForSBA(apStartDate: LocalDate, apEndDate: LocalDate, contractDate: LocalDate, cost: BigDecimal): Int = {
    val dailyRate = apportionedCostOfBuilding(cost, daysBetween(apStartDate, apEndDate))

    val  totalCost = if(isEarliestWrittenContractAfterAPStart(contractDate, apStartDate)) {
      daysBetween(contractDate, apEndDate) * dailyRate
    } else {
      daysBetween(apStartDate, apEndDate) * dailyRate
    }

    roundedToIntHalfUp(totalCost)

  }
}
