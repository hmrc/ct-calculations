/*
 * Copyright 2016 HM Revenue & Customs
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

import org.joda.time.{Days, LocalDate}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod

object AccountingPeriodHelper {

  def daysInAccountingPeriod(accountingPeriod: HmrcAccountingPeriod) = daysBetween(accountingPeriod.cp1.value, accountingPeriod.cp2.value)

  def accountingPeriodDaysInFinancialYear(year: Int, accountingPeriod: HmrcAccountingPeriod): BigDecimal = {
    val (fyStartDate, fyEndDate) = financialYearStartingIn(year)
    val start = if (accountingPeriod.cp1.value.isBefore(fyStartDate)) fyStartDate else accountingPeriod.cp1.value
    val end = if (accountingPeriod.cp2.value.isAfter(fyEndDate)) fyEndDate else accountingPeriod.cp2.value
    BigDecimal(daysBetween(start, end))
  }

  def accountingPeriodSpansTwoFinancialYears(accountingPeriod: HmrcAccountingPeriod): Boolean = {
    fallsInFinancialYear(accountingPeriod.cp2.value) > fallsInFinancialYear(accountingPeriod.cp1.value)
  }

  def financialYearStartingIn(year: Int): (LocalDate, LocalDate) = (new LocalDate(year, 4, 1), new LocalDate(year + 1, 3, 31))

  def fallsInFinancialYear(date: LocalDate): Int = if (date.getMonthOfYear < 4) date.getYear - 1 else date.getYear

  def daysBetween(start: LocalDate, end: LocalDate): Int = Days.daysBetween(start, end).getDays + 1

  def validateAccountingPeriod(accountingPeriod: HmrcAccountingPeriod) = {
    if (accountingPeriod.cp1.value.isAfter(accountingPeriod.cp2.value)) {
      throw new InvalidAccountingPeriodException("Accounting Period start date must be before the end date")
    }
    
    if (daysBetween(accountingPeriod.cp1.value, accountingPeriod.cp2.value) > maximumNumberOfDaysInAccountingPeriod(accountingPeriod)) {
      throw new InvalidAccountingPeriodException("Accounting Period must not be longer than one calendar year")
    }
    
    if (accountingPeriod.cp1.value.isBefore(new LocalDate(2006, 10, 2))) {
      throw new InvalidAccountingPeriodException("Accounting Period must not be before 1st October 2006")
    }
  }

  private def maximumNumberOfDaysInAccountingPeriod(accountingPeriod: HmrcAccountingPeriod): BigDecimal = {
    val startDate = accountingPeriod.cp1.value
    val endDate = startDate.withYear(startDate.getYear + 1)
    daysBetween(startDate, endDate) - 1
  }
}

class InvalidAccountingPeriodException(message: String) extends Exception(message)
