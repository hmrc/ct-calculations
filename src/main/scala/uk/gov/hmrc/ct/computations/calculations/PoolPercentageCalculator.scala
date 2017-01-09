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

import org.joda.time.{Days, LocalDate}
import uk.gov.hmrc.ct.computations.{CP1, CP2}
import uk.gov.hmrc.ct.utils.DateImplicits._

import scala.math.BigDecimal.RoundingMode


case class PoolPercentageCalculator(oldMainRate: Int = 20,
                                    newMainRate: Int = 18,
                                    oldSpecialRate: Int = 10,
                                    newSpecialRate: Int = 8,
                                    newRateStartDate: LocalDate = new LocalDate("2012-04-01")) {

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

  private def daysInPeriodIncludingStartAndEndDays(startDate: LocalDate, endDate: LocalDate): Int = Days.daysBetween(startDate, endDate.plusDays(1)).getDays

  private[calculations] def daysBefore(startDate: LocalDate, endDate: LocalDate):  Int = {
    (startDate < newRateStartDate, endDate < newRateStartDate) match {
      case (_, _) if endDate < startDate => throw new IllegalArgumentException("start date must be before end date!")
      case (true, false) => Days.daysBetween(startDate, newRateStartDate).getDays
      case (true, true) => 1 + Days.daysBetween(startDate, newRateStartDate).getDays - Days.daysBetween(endDate, newRateStartDate).getDays // 1 is to include start day
      case (_, _) => 0
    }

  }

  private[calculations] def daysOnAndAfter(startDate: LocalDate, endDate: LocalDate): Int = {
    (startDate < newRateStartDate, endDate < newRateStartDate) match {
      case (_, _) if endDate < startDate => throw new IllegalArgumentException("start date must be before end date!")
      case (true, false) => 1 + Days.daysBetween(newRateStartDate, endDate).getDays // 1 is to include start day
      case (false, false) => 1 + Days.daysBetween(newRateStartDate, endDate).getDays - Days.daysBetween(newRateStartDate, startDate).getDays  // the 1 is to include start day
      case (_, _) => 0
    }
  }

}
