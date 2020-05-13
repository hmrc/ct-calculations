/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.ct600.NumberRounding
import uk.gov.hmrc.ct.ct600.calculations.AccountingPeriodHelper

trait SBACalculator extends NumberRounding with AccountingPeriodHelper {

  val rate: BigDecimal = 0.02

  def getDaysIntheYear(apStartDate: LocalDate) = {
    val yearAfterApStart = apStartDate.plusYears(1)
    if(apStartDate.getDayOfMonth == yearAfterApStart.getDayOfMonth) daysBetween(apStartDate, yearAfterApStart) - 1 else 366
  }

  def apportionedCostOfBuilding(cost: BigDecimal, daysInTheYear: Int): BigDecimal = (cost * rate) / daysInTheYear

  def isEarliestWrittenContractAfterAPStart(contractDate: LocalDate, apStartDate: LocalDate): Boolean = contractDate.isAfter(apStartDate)

  /* This is the 2% rounded up*/
  def getAmountClaimableForSBA(apStartDate: LocalDate, apEndDate: LocalDate, firstUsage: Option[LocalDate], cost: Option[Int]): Option[Int] = {
    val daysInTheYear = getDaysIntheYear(apStartDate)

    (firstUsage, cost) match {
      case (Some(firstUsageDate), Some(maybeCost)) => {
        val dailyRate = apportionedCostOfBuilding(maybeCost, daysInTheYear)

        val totalCost = if (isEarliestWrittenContractAfterAPStart(firstUsageDate, apStartDate)) {
          daysBetween(firstUsageDate, apEndDate) * dailyRate
        } else {
          daysBetween(apStartDate, apEndDate) * dailyRate
        }

        Some(roundedToIntHalfUp(totalCost))

      }
      case _ => None
    }

  }

  //we can also use cats to make this look better.
  def sumAmount(xs:List[Option[Int]]) = {
    xs.fold(Option(0)){
      (accumulator, element) =>
        for {
          accum <- accumulator
          ele <- element
        } yield accum + ele
    }
  }
}
