/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.calculations

import org.joda.time.{Days, LocalDate}
import uk.gov.hmrc.ct.box.{EndDate, StartDate}
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.utils.DateImplicits._

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

  def financialYearStartingIn(year: Int): (LocalDate, LocalDate) = (new LocalDate(year, 4, 1), new LocalDate(year + 1, 3, 31))

  def startingFinancialYear(date: StartDate): Int = financialYearForDate(date.value)

  def endingFinancialYear(date: EndDate): Int = financialYearForDate(date.value)

  private def financialYearForDate(date: LocalDate): Int = if (date.getMonthOfYear < 4) date.getYear - 1 else date.getYear

  def daysBetween(start: LocalDate, end: LocalDate): Int = Days.daysBetween(start, end).getDays + 1

  def validateAccountingPeriod(accountingPeriod: HmrcAccountingPeriod) = {
    if (accountingPeriod.start.value > accountingPeriod.end.value) {
      throw new InvalidAccountingPeriodException("Accounting Period start date must be before the end date")
    }
    
    if (daysBetween(accountingPeriod.start.value, accountingPeriod.end.value) > maximumNumberOfDaysInAccountingPeriod(accountingPeriod)) {
      throw new InvalidAccountingPeriodException("Accounting Period must not be longer than one calendar year")
    }
    
    if (accountingPeriod.start.value < new LocalDate(2006, 10, 2)) {
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
