/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.accounts.frsse2008.calculations

import org.joda.time.{LocalDate, Period, PeriodType}


object PeriodCalculator {

  def periodHeadingComponents(startDate: LocalDate, endDate: LocalDate): PeriodHeadingComponents = {
    val friendlyEndDate = endDate.toString("d MMM yyyy")
    val yearEndDate = endDate.toString("yyyy")
    val months = monthsInPeriod(startDate, endDate)

    months match {
      case 12 => PeriodHeadingComponents(monthCount = 12, messageKey = "", dateText = yearEndDate)
      case months if (months > 1) => PeriodHeadingComponents(monthCount = months, messageKey = "periodHeader.plural", dateText = friendlyEndDate)
      case _ => PeriodHeadingComponents(monthCount = months, messageKey = "periodHeader.singular", dateText = friendlyEndDate)
    }
  }

  private def monthsInPeriod(startDate: LocalDate, endDate: LocalDate): Int = {
    val period = new Period(startDate.minusDays(1), endDate, PeriodType.yearMonthDay().withYearsRemoved())

    period match {
      case p if p.getDays > 15 => p.getMonths + 1
      case p if p.getMonths < 1 => 1
      case p => p.getMonths
    }
  }
}

case class PeriodHeadingComponents(monthCount: Int, messageKey: String, dateText:  String)
