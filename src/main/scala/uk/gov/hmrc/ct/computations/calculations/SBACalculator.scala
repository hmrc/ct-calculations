/*
 * Copyright 2020 HM Revenue & Customs
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

import org.joda.time.LocalDate
import uk.gov.hmrc.ct.computations.capitalAllowanceAndSBA.{SBARate, SBAResults}
import uk.gov.hmrc.ct.ct600.NumberRounding
import uk.gov.hmrc.ct.ct600.calculations.AccountingPeriodHelper

trait SBACalculator extends NumberRounding with AccountingPeriodHelper {

 private type SBARateForTaxYear = BigDecimal
 private val endOfTaxYear2019 = LocalDate.parse("2020-03-31")

  val ratePriorTy2020: SBARateForTaxYear = 0.02
  val rateAfterTy2020: SBARateForTaxYear = 0.03

  def getDaysIntheYear(apStartDate: LocalDate): Int = {
    val yearAfterApStart = apStartDate.plusYears(1)
    if (apStartDate.getDayOfMonth == yearAfterApStart.getDayOfMonth) daysBetween(apStartDate, yearAfterApStart) - 1 else 366
  }

  def apportionedCostOfBuilding(cost: BigDecimal, daysInTheYear: Int, rateForTy: SBARateForTaxYear): BigDecimal = (cost * rateForTy) / daysInTheYear

  def isEarliestWrittenContractAfterAPStart(contractDate: LocalDate, apStartDate: LocalDate): Boolean = contractDate.isAfter(apStartDate)

  def getSBADetails(apStartDate: LocalDate, apEndDate: LocalDate, maybeFirstUsageDate: Option[LocalDate], maybeCost: Option[Int]): Option[SBAResults] = {

    (maybeFirstUsageDate, maybeCost) match {
      case (Some(firstUsageDate), Some(cost)) => {

        val daysInTheYear = getDaysIntheYear(apStartDate)
        val isAfterTy2020 = if (financialYearForDate(apEndDate) >= 2020) true else false

        val dailyRateAfter2020 = apportionedCostOfBuilding(cost, daysInTheYear, rateAfterTy2020)
        val dailyRateBefore2020 = apportionedCostOfBuilding(cost, daysInTheYear, ratePriorTy2020)


        //todo refactor this can be cut down in size
        val sbaResult: Option[SBAResults] = if (isEarliestWrittenContractAfterAPStart(firstUsageDate, apStartDate)) {

          if (isAfterTy2020) {
            dealWith2020Logic(firstUsageDate, apEndDate, dailyRateAfter2020, dailyRateBefore2020)
          }
          else {
            val daysToApplyRate = daysBetween(firstUsageDate, apEndDate)
            Some(SBAResults(ratePriorTaxYear2020 = SBARate(daysToApplyRate, dailyRateBefore2020, dailyRateBefore2020)))
          }
        }
        else {
          if (isAfterTy2020) dealWith2020Logic(apStartDate, apEndDate, dailyRateAfter2020, dailyRateBefore2020)
          else {
            Some(SBAResults(ratePriorTaxYear2020 = SBARate(daysBetween(apStartDate, apEndDate), dailyRateBefore2020, dailyRateBefore2020)))
          }
        }
        sbaResult
      }
      case _ => None
    }
  }

  private def dealWith2020Logic(chargeStartDate: LocalDate,
                                apEndDate: LocalDate,
                                dailyRateAfter2020: BigDecimal,
                                dailyRateBefore2020: BigDecimal): Option[SBAResults] = {
    val daysBeforeRateChange: Int =
      if(chargeStartDate.isBefore(endOfTaxYear2019)) daysBetween(chargeStartDate, endOfTaxYear2019)
    else 0
    val totalDaysCharged: Int = daysBetween(chargeStartDate, apEndDate)
    val daysAfter2020 = totalDaysCharged - daysBeforeRateChange
    Some(SBAResults(ratePriorTaxYear2020 = SBARate(daysBeforeRateChange, dailyRateBefore2020, ratePriorTy2020), ratePostTaxYear2020 = Some(SBARate(daysAfter2020, dailyRateAfter2020, rateAfterTy2020))))
  }


  //we can also use cats to make this look better.
  def sumAmount(xs: List[Option[Int]]) = {
    xs.fold(Option(0)) {
      (accumulator, element) =>
        for {
          accum <- accumulator
          ele <- element
        } yield accum + ele
    }
  }
}
