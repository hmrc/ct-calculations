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

import uk.gov.hmrc.ct.computations.{CP1, CP2}

import java.time.temporal.ChronoUnit
import java.time.LocalDate
import scala.math.BigDecimal.RoundingMode


case class PoolPercentageCalculator(oldMainRate: Int = 18,
                                    newMainRate: Int = 18,
                                    oldSpecialRate: Int = 8,
                                    newSpecialRate: Int = 6,
                                    newRateStartDate: LocalDate = LocalDate.parse("2019-04-01")) {

  def apportionedMainRate(cp1: CP1, cp2: CP2): BigDecimal = {
    val daysInAP = daysInPeriodIncludingStartAndEndDays(cp1.value, cp2.value)

    val apportionedMainRateBefore = BigDecimal(daysBefore(cp1.value, cp2.value)) / BigDecimal(daysInAP) * BigDecimal(oldMainRate)
    val apportionedMainRateOnAndAfter = BigDecimal(daysOnAndAfter(cp1.value, cp2.value)) / BigDecimal(daysInAP) * BigDecimal(newMainRate)

    (apportionedMainRateBefore + apportionedMainRateOnAndAfter).setScale(2, RoundingMode.HALF_UP)
  }

  def apportionedSpecialRate(cp1: CP1, cp2: CP2): BigDecimal = {
    val daysInAP = daysInPeriodIncludingStartAndEndDays(cp1.value, cp2.value)

    val apportionedSpecialRateBefore = BigDecimal(daysBefore(cp1.value, cp2.value)) / BigDecimal(daysInAP) * BigDecimal(oldSpecialRate)
    val apportionedSpecialRateOnAndAfter = BigDecimal(daysOnAndAfter(cp1.value, cp2.value)) / BigDecimal(daysInAP) * BigDecimal(newSpecialRate)

    (apportionedSpecialRateBefore + apportionedSpecialRateOnAndAfter).setScale(2, RoundingMode.HALF_UP)
  }

  private def daysInPeriodIncludingStartAndEndDays(startDate: LocalDate, endDate: LocalDate): Long = startDate.until(endDate, ChronoUnit.DAYS) + 1

  private[calculations] def daysBefore(startDate: LocalDate, endDate: LocalDate):  Long = {
    (startDate.isBefore(newRateStartDate), endDate.isBefore(newRateStartDate)) match {
      case (_, _) if endDate.isBefore(startDate) => throw new IllegalArgumentException("start date must be before end date!")
      case (true, false) => startDate.until(newRateStartDate, ChronoUnit.DAYS)
      case (true, true) => 1 + startDate.until(newRateStartDate, ChronoUnit.DAYS) - endDate.until(newRateStartDate, ChronoUnit.DAYS) // 1 is to include start day
      case (_, _) => 0
    }

  }

  private[calculations] def daysOnAndAfter(startDate: LocalDate, endDate: LocalDate): Long = {
    (startDate.isBefore(newRateStartDate), endDate.isBefore(newRateStartDate)) match {
      case (_, _) if endDate.isBefore(startDate) => throw new IllegalArgumentException("start date must be before end date!")
      case (true, false) => 1 + newRateStartDate.until(endDate, ChronoUnit.DAYS) // 1 is to include start day
      case (false, false) => 1 + newRateStartDate.until(endDate, ChronoUnit.DAYS) - newRateStartDate.until(startDate, ChronoUnit.DAYS)  // the 1 is to include start day
      case (_, _) => 0
    }
  }

}
