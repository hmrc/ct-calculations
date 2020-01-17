package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.computations.{CP1, CP2}
import uk.gov.hmrc.ct.ct600.NumberRounding
import uk.gov.hmrc.ct.ct600.calculations.AccountingPeriodHelper

trait SBACalculator extends NumberRounding with AccountingPeriodHelper{

  val rate: BigDecimal

  def daysInyear(year: LocalDate): Int = if (year.getYear % 4 == 0) 366 else 365

  def getDaysInAP(apStart: CP1, apEnd: CP2): Int = daysBetween(apStart.value, apEnd.value)

  def apportionedCostOfBuilding(cost: Int, daysIntTheYear: Int): BigDecimal = (cost * rate) / daysIntTheYear

  def getDaysBetweenEarliestWrittenContractAndEndDate(dateOfPurchase: LocalDate, apEndDate: CP2): Int = daysBetween(dateOfPurchase, apEndDate.value)

  def isEarliestWrittenContractAfterAPStart(contractDate: LocalDate, apStartDate: LocalDate): Boolean = apStartDate.isAfter(contractDate)

  /* This is the 2% */
  def getAmountClaimableForSBA(apStartDate: CP1, apEndDate: CP2, contractDate: LocalDate,cost: Int): Int = {
    val dailyRate = apportionedCostOfBuilding(cost, daysInyear(apStartDate.value))

    println("Daily Rate " + dailyRate)

    val totalCost = if(isEarliestWrittenContractAfterAPStart(contractDate, apEndDate.value)) {
      daysBetween(contractDate, apEndDate.value) * dailyRate
    } else {
      getDaysInAP(apStartDate, apEndDate) * dailyRate
    }

    roundedToIntHalfUp(totalCost)

  }
}
