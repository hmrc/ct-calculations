/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ct.computations.calculations

import org.joda.time.{Days, LocalDate, Months}
import uk.gov.hmrc.ct.CATO02
import uk.gov.hmrc.ct.computations.{CP1, CP2}
import uk.gov.hmrc.ct.utils.DateImplicits._

import scala.math.BigDecimal.RoundingMode

trait AnnualInvestmentAllowanceCalculator {

  val DaysInYear   = 365
  val MonthsInYear = 12

  def maximum(cp1: CP1, cp2: CP2, allowableAmounts: Set[AnnualInvestmentAllowancePeriod]): CATO02 = {
    val maximumUsingDailyBasis = maximumEntitlementForAccountingPeriod(false) _
    val maximumUsingMonthlyBasis = maximumEntitlementForAccountingPeriod(true) _

    CATO02(
      if(consistsOfCalendarMonths(cp1, cp2))
        maximumUsingDailyBasis(cp1, cp2, allowableAmounts) max maximumUsingMonthlyBasis(cp1, cp2, allowableAmounts)
      else
        maximumUsingDailyBasis(cp1, cp2, allowableAmounts)
    )
  }

  //Apportion by length of AP unless covers an AIA Period Start date with it's date range, in which case we always apportion by 365
  //This is to give the benefit of the calculation to the client when they cover a change in allowance on a leap year
  protected def yearLengthForDailyBasis(accountingPeriodStartDate: LocalDate, accountingPeriodEndDate: LocalDate): Int =
    AnnualInvestmentAllowancePeriods().filter(_.startDateFallsWithin(accountingPeriodStartDate, accountingPeriodEndDate)) match {
      case aiaPeriodsStartingInAccountingPeriod if aiaPeriodsStartingInAccountingPeriod.nonEmpty => DaysInYear
      case _ => {
        val accountingPeriodLength = daysBetween(accountingPeriodStartDate, accountingPeriodEndDate)
        accountingPeriodLength max DaysInYear
      }
    }

  private def maximumEntitlementForAccountingPeriod(useMonthlyBasis: Boolean)(cp1: CP1, cp2: CP2, allowableAmounts: Set[AnnualInvestmentAllowancePeriod]): Int = {
    val yearLengthForBasis = if(useMonthlyBasis) MonthsInYear else yearLengthForDailyBasis(cp1.value, cp2.value)

    allowableAmounts.toList.map { aiaPeriod =>
      val intersectingPeriod = if(useMonthlyBasis)
        aiaPeriod.intersectingPeriod(cp1.value, cp2.value, monthsBetween)
      else
        aiaPeriod.intersectingPeriod(cp1.value, cp2.value, daysBetween)

      val proportionOfAIAPeriod = BigDecimal(intersectingPeriod) / BigDecimal(yearLengthForBasis)

      val proRatedForIntersectingPeriod = BigDecimal(aiaPeriod.maximumAllowed) * proportionOfAIAPeriod

      proRatedForIntersectingPeriod.setScale(0, RoundingMode.UP).toInt
    }.sum
  }

  private def daysBetween(start: LocalDate, end: LocalDate) = Days.daysBetween(start, end).getDays + 1
  private def monthsBetween(start: LocalDate, end: LocalDate): Int = Months.monthsBetween(start, end.plusDays(1)).getMonths

  private def consistsOfCalendarMonths(cp1: CP1, cp2: CP2): Boolean = {
    def isFirstDayOfMonth(date: LocalDate) = date == date.dayOfMonth.withMinimumValue
    def isLastDayOfMonth(date: LocalDate) = date == date.dayOfMonth.withMaximumValue

    isFirstDayOfMonth(cp1.value) && isLastDayOfMonth(cp2.value)
  }
}

case class AnnualInvestmentAllowancePeriod(start: LocalDate, end: LocalDate, maximumAllowed: Int) {

  type ComputePeriodBetween = (LocalDate, LocalDate) => Int

  require(start == start.dayOfMonth.withMinimumValue, "Start date must be a month start date")
  require(end == end.dayOfMonth.withMaximumValue, "End date must be a month end date")

  def encompasses(date: LocalDate): Boolean = !(date < start) && !(date > end)

  def fallsWithin(startDate: LocalDate, endDate: LocalDate): Boolean = startDate < start && endDate > end

  def startDateFallsWithin(startDate: LocalDate, endDate: LocalDate): Boolean = start >= startDate && start <= endDate

  def intersectingPeriod(periodStart: LocalDate, periodEnd: LocalDate, periodBetween: ComputePeriodBetween): Int =
    (encompasses(periodStart), encompasses(periodEnd), fallsWithin(periodStart, periodEnd)) match {
      case (true, true, false) => periodBetween(periodStart, periodEnd)
      case (true, false, false) => periodBetween(periodStart, end)
      case (false, true, false) => periodBetween(start, periodEnd)
      case (false, false, true) => periodBetween(start, end)
      case _ => 0
    }
}

object AnnualInvestmentAllowancePeriods {

  def apply() = Set(
    AnnualInvestmentAllowancePeriod(start = new LocalDate(2017, 1, 1), end = new LocalDate(2999, 12, 31), maximumAllowed = 200000),
    AnnualInvestmentAllowancePeriod(start = new LocalDate(2016, 1, 1), end = new LocalDate(2016, 12, 31), maximumAllowed = 200000),
    AnnualInvestmentAllowancePeriod(start = new LocalDate(2014, 4, 1), end = new LocalDate(2015, 12, 31), maximumAllowed = 500000),
    AnnualInvestmentAllowancePeriod(start = new LocalDate(2013, 1, 1), end = new LocalDate(2014, 3, 31),  maximumAllowed = 250000),
    AnnualInvestmentAllowancePeriod(start = new LocalDate(2012, 4, 1), end = new LocalDate(2012, 12, 31), maximumAllowed = 25000),
    AnnualInvestmentAllowancePeriod(start = new LocalDate(2010, 4, 1), end = new LocalDate(2012, 3, 31),  maximumAllowed = 100000),
    AnnualInvestmentAllowancePeriod(start = new LocalDate(2008, 4, 1), end = new LocalDate(2010, 3, 31),  maximumAllowed = 50000)
  )
}
