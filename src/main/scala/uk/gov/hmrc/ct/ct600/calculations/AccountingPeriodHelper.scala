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

package uk.gov.hmrc.ct.ct600.calculations

import uk.gov.hmrc.ct.box.{EndDate, StartDate}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.utils.DateImplicits._

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object AccountingPeriodHelper extends AccountingPeriodHelper

trait AccountingPeriodHelper {

  def daysInAccountingPeriod(accountingPeriod: HmrcAccountingPeriod) = daysBetween(accountingPeriod.start.value, accountingPeriod.end.value)

  def accountingPeriodDaysInFinancialYear(year: Int, accountingPeriod: HmrcAccountingPeriod): BigDecimal = {
    val (fyStartDate, fyEndDate) = financialYearStartingIn(year)
    val start = if (accountingPeriod.start.value < fyStartDate) fyStartDate else accountingPeriod.start.value
    val end = if (accountingPeriod.end.value > fyEndDate) fyEndDate else accountingPeriod.end.value
    BigDecimal(daysBetween(start, end))
  }

  def accountingPeriodSpansTwoFinancialYears(accountingPeriod: HmrcAccountingPeriod): Boolean = {
    endingFinancialYear(accountingPeriod.end) > startingFinancialYear(accountingPeriod.start)
  }

  def financialYearStartingIn(year: Int): (LocalDate, LocalDate) = (LocalDate.of(year, 4, 1), LocalDate.of(year + 1, 3, 31))

  def startingFinancialYear(date: StartDate): Int = financialYearForDate(date.value)

  def endingFinancialYear(date: EndDate): Int = financialYearForDate(date.value)

   def financialYearForDate(date: LocalDate): Int = if (date.getMonthValue < 4) date.getYear - 1 else date.getYear

  def daysBetween(start: LocalDate, end: LocalDate): Long = start.until(end, ChronoUnit.DAYS) + 1

  def validateAccountingPeriod(accountingPeriod: HmrcAccountingPeriod) = {
    if (accountingPeriod.start.value > accountingPeriod.end.value) {
      throw new InvalidAccountingPeriodException("Accounting Period start date must be before the end date")
    }

    val i = daysBetween(accountingPeriod.start.value, accountingPeriod.end.value)
    val decimal = maximumNumberOfDaysInAccountingPeriod(accountingPeriod)
    if (i > decimal) {
      throw new InvalidAccountingPeriodException("Accounting Period must not be longer than one calendar year")
    }
    
    if (accountingPeriod.start.value < LocalDate.of(2006,10,2)) {
      throw new InvalidAccountingPeriodException("Accounting Period must not be before 1st October 2006")
    }
  }

  private def maximumNumberOfDaysInAccountingPeriod(accountingPeriod: HmrcAccountingPeriod): BigDecimal = {
    val startDate = accountingPeriod.start.value
    val endDate = startDate.withYear(startDate.getYear + 1)
    daysBetween(startDate, endDate) - 1
  }
}

class InvalidAccountingPeriodException(message: String) extends Exception(message)
