/*
 * Copyright 2015 HM Revenue & Customs
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
import play.api.libs.json.{Format, Json}
import uk.gov.hmrc.ct.computations.CP6

import scala.math.BigDecimal.RoundingMode

trait ApportionedProfitAndLossCalculator {

  def apportionmentProfitOrLossCalculation(params: ApportionmentProfitOrLossParameters): CP6 = {

    val totalPeriodDays = Days.daysBetween(params.accountStartDate, params.accountEndDate).getDays + 1

    val startDate: LocalDate = params.periodOfAccountsStart.getOrElse(params.accountStartDate)
    val endDate: LocalDate = params.periodOfAccountEnd.getOrElse(params.accountEndDate)

    val daysBefore = Days.daysBetween(params.accountStartDate, startDate).getDays
    val daysAfter = Days.daysBetween(endDate, params.accountEndDate).getDays
    val daysDuring = totalPeriodDays - daysBefore - daysAfter

    def calculateProfitLossInPeriod(periodDays: Int): Option[Int] = {
      if (periodDays == 0) {
        None
      }
      else {
        val pAndL: BigDecimal = BigDecimal.valueOf(params.profitOrLoss)

        val percentageDays: BigDecimal = BigDecimal.valueOf(periodDays) / BigDecimal.valueOf(totalPeriodDays)

        Some((pAndL * percentageDays).setScale(0, RoundingMode.DOWN).toInt)
      }
    }

    val result = ApportionmentProfitOrLossResult(profitAndLossBeforePeriodOfAccounts = calculateProfitLossInPeriod(daysBefore),
                                                      profitAndLossDuringPeriodOfAccounts = calculateProfitLossInPeriod(daysDuring),
                                                      profitAndLossAfterPeriodOfAccounts = calculateProfitLossInPeriod(daysAfter),
                                                      accountPeriodStart = startDate,
                                                      accountPeriodEnd = endDate)

    CP6(
      if (result.total != params.profitOrLoss) {
        val diff = params.profitOrLoss - result.total
        result match {
          case ApportionmentProfitOrLossResult(Some(b), Some(d), Some(a), _, _) if diff % 2 == 0 =>
            result.copy(profitAndLossBeforePeriodOfAccounts = Some(b + diff / 2), profitAndLossAfterPeriodOfAccounts = Some(a + diff / 2))

          case ApportionmentProfitOrLossResult(Some(b), Some(d), _, _, _) =>
            result.copy(profitAndLossBeforePeriodOfAccounts = Some(b + diff))

          case ApportionmentProfitOrLossResult(None, Some(d), Some(a), _, _) =>
            result.copy(profitAndLossAfterPeriodOfAccounts = Some(a + diff))

          case _ => throw new IllegalStateException("Cannot have non-matching total and profit and loss")
        }
      } else result
    )
  }

}

case class ApportionmentProfitOrLossParameters(accountStartDate: LocalDate,
                                               accountEndDate: LocalDate,
                                               profitOrLoss: Int,
                                               periodOfAccountsStart: Option[LocalDate],
                                               periodOfAccountEnd: Option[LocalDate])

case class ApportionmentProfitOrLossResult(profitAndLossBeforePeriodOfAccounts: Option[Int],
                                           profitAndLossDuringPeriodOfAccounts: Option[Int],
                                           profitAndLossAfterPeriodOfAccounts: Option[Int],
                                           accountPeriodStart: LocalDate,
                                           accountPeriodEnd: LocalDate) {

  def total = profitAndLossAfterPeriodOfAccounts.getOrElse(0) + profitAndLossBeforePeriodOfAccounts.getOrElse(0) + profitAndLossDuringPeriodOfAccounts.getOrElse(0)

  def hasBefore: Boolean = profitAndLossBeforePeriodOfAccounts.isDefined

  def hasAfter: Boolean = profitAndLossAfterPeriodOfAccounts.isDefined
}

object ApportionmentProfitOrLossResult {

  implicit val apportionmentResultFormat: Format[ApportionmentProfitOrLossResult] = Json.format[ApportionmentProfitOrLossResult]
}
