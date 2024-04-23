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

package uk.gov.hmrc.ct.accounts.frsse2008.calculations

import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.{LocalDate, Period}


object PeriodCalculator {

  def periodHeadingComponents(startDate: LocalDate, endDate: LocalDate): PeriodHeadingComponents = {
    val friendlyEndDate = endDate.format(DateTimeFormatter.ofPattern("d MMM yyyy"))
    val yearEndDate = endDate.format(DateTimeFormatter.ofPattern("yyyy"))
    val months = monthsInPeriod(startDate, endDate)

    months match {
      case 12 => PeriodHeadingComponents(monthCount = 12, messageKey = "", dateText = yearEndDate)
      case months if months > 1 => PeriodHeadingComponents(monthCount = months, messageKey = "periodHeader.plural", dateText = friendlyEndDate)
      case _ => PeriodHeadingComponents(monthCount = months, messageKey = "periodHeader.singular", dateText = friendlyEndDate)
    }
  }

  private def monthsInPeriod(startDate: LocalDate, endDate: LocalDate): Long = {
    val p = Period.between(startDate.minusDays(1), endDate)
    val months = ChronoUnit.MONTHS.between(startDate.minusDays(1), endDate)

    p match {
      case p if p.getDays > 15 => months + 1
      case _ if months < 1 => 1
      case _ => months
    }
  }
}

case class PeriodHeadingComponents(monthCount: Long, messageKey: String, dateText:  String)
