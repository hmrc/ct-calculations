/*
 * Copyright 2024 HM Revenue & Customs
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

import uk.gov.hmrc.ct.CATO02
import uk.gov.hmrc.ct.computations.{CP1, CP2}

import java.time.LocalDate
import java.time.temporal.{ChronoUnit, TemporalAdjusters}
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
  protected def yearLengthForDailyBasis(accountingPeriodStartDate: LocalDate, accountingPeriodEndDate: LocalDate): Long =
    AnnualInvestmentAllowancePeriods().filter(_.startDateFallsWithin(accountingPeriodStartDate, accountingPeriodEndDate)) match {
      case aiaPeriodsStartingInAccountingPeriod if aiaPeriodsStartingInAccountingPeriod.nonEmpty => DaysInYear
      case _ =>
        val accountingPeriodLength = daysBetween(accountingPeriodStartDate, accountingPeriodEndDate)
        accountingPeriodLength max DaysInYear
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

  private def daysBetween(start: LocalDate, end: LocalDate) = start.until(end, ChronoUnit.DAYS) + 1
  private def monthsBetween(start: LocalDate, end: LocalDate): Long = start.until(end.plusDays(1), ChronoUnit.MONTHS)

  private def consistsOfCalendarMonths(cp1: CP1, cp2: CP2): Boolean = {
    def isFirstDayOfMonth(date: LocalDate) = date == date.`with`(TemporalAdjusters.firstDayOfMonth())
    def isLastDayOfMonth(date: LocalDate) = date == date.`with`(TemporalAdjusters.lastDayOfMonth())

    isFirstDayOfMonth(cp1.value) && isLastDayOfMonth(cp2.value)
  }
}

case class AnnualInvestmentAllowancePeriod(start: LocalDate, end: LocalDate, maximumAllowed: Int) {

  type ComputePeriodBetween = (LocalDate, LocalDate) => Long

  require(start == start.`with`(TemporalAdjusters.firstDayOfMonth()), "Start date must be a month start date")
  require(end == end.`with`(TemporalAdjusters.lastDayOfMonth()), "End date must be a month end date")

  def encompasses(date: LocalDate): Boolean = !date.isBefore(start) && !date.isAfter(end)

  def fallsWithin(startDate: LocalDate, endDate: LocalDate): Boolean = startDate.isBefore(start) && endDate.isAfter(end)

  def startDateFallsWithin(startDate: LocalDate, endDate: LocalDate): Boolean = startDate.isBefore(start) && endDate.isAfter(start)

  def intersectingPeriod(periodStart: LocalDate, periodEnd: LocalDate, periodBetween: ComputePeriodBetween): Long =
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
    AnnualInvestmentAllowancePeriod(start = LocalDate.of(2017,1,1), end = LocalDate.of(2999,12,31), maximumAllowed = 200000),
    AnnualInvestmentAllowancePeriod(start = LocalDate.of(2016,1,1), end = LocalDate.of(2016,12,31), maximumAllowed = 200000),
    AnnualInvestmentAllowancePeriod(start = LocalDate.of(2014,4,1), end = LocalDate.of(2015,12,31), maximumAllowed = 500000),
    AnnualInvestmentAllowancePeriod(start = LocalDate.of(2013,1,1), end = LocalDate.of(2014,3,31),  maximumAllowed = 250000),
    AnnualInvestmentAllowancePeriod(start = LocalDate.of(2012,4,1), end = LocalDate.of(2012,12,31), maximumAllowed = 25000),
    AnnualInvestmentAllowancePeriod(start = LocalDate.of(2010,4,1), end = LocalDate.of(2012,3,31),  maximumAllowed = 100000),
    AnnualInvestmentAllowancePeriod(start = LocalDate.of(2008,4,1), end = LocalDate.of(2010,3,31),  maximumAllowed = 50000)
  )
}
