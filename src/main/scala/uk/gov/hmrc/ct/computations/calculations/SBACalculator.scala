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


//todo make make stronly typed so we dont mix up dailyRate and rateYearlyPercentage
case class SbaRate(numberOfDaysRate: Int, dailyRate: BigDecimal, rateYearlyPercentage: BigDecimal) extends NumberRounding {
  val rateYearlyPercentageAsInt = roundedToInt(rateYearlyPercentage * 100)
  val costRate = roundedToIntHalfUp(numberOfDaysRate * dailyRate)
}
//todo make this a list of different  rates and just put the year in it ?
case class SbaResults(ratePrior2020: Option[SbaRate] = None, rate2020: Option[SbaRate] = None) extends NumberRounding {
  val maybeTotalCodeBefore2020 =   ratePrior2020.map(_.costRate).getOrElse(0)
  val maybeTotalCodeAfter2020 =   rate2020.map(_.costRate).getOrElse(0)
  val totalCost: Option[Int] = Some(maybeTotalCodeBefore2020 + maybeTotalCodeAfter2020)
}


trait SBACalculator extends NumberRounding with AccountingPeriodHelper {

  private type SBARateForTaxYear = BigDecimal // might not get much value out of this
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

        val isAfterTy2020 = financialYearForDate(apEndDate) >= 2020

        val dailyRateAfter2020 = apportionedCostOfBuilding(cost, daysInTheYear, rateAfterTy2020)
        val dailyRateBefore2020 = apportionedCostOfBuilding(cost, daysInTheYear, ratePriorTy2020)


        val daysToApplyTax = if(isEarliestWrittenContractAfterAPStart(firstUsageDate, apStartDate)) firstUsageDate else apStartDate
        val sbaResult: Option[SBAResults] = (isAfterTy2020) match {
          case true => apportioningRateAfterTaxYear2020(daysToApplyTax, apEndDate, dailyRateAfter2020, dailyRateBefore2020)
          case false => Some(SBAResults(ratePriorTaxYear2020 = Some(SBARate(daysBetween(daysToApplyTax, apEndDate), dailyRateBefore2020, dailyRateBefore2020))))
        }
        sbaResult
        }
      case _ => None
      }
  }

  private def apportioningRateAfterTaxYear2020(daysToApplyTax: LocalDate,
                                apEndDate: LocalDate,
                                dailyRateAfter2020: BigDecimal,
                                dailyRateBefore2020: BigDecimal): Option[SBAResults] = {
    val splitRate: Boolean = daysToApplyTax.isBefore(endOfTaxYear2019) && apEndDate.isAfter(endOfTaxYear2019)
    val totalDaysCharged: Int = daysBetween(daysToApplyTax, apEndDate)

    if(splitRate){
      val daysBefore2020 =  daysBetween(daysToApplyTax, endOfTaxYear2019)
      val daysAfter2020 = totalDaysCharged - daysBetween(daysToApplyTax, endOfTaxYear2019)
      val sbaRateAfter2020 = SBARate(daysAfter2020, dailyRateAfter2020, rateAfterTy2020)
      val sbaRatePrior2020 = SBARate(daysBefore2020, dailyRateBefore2020, ratePriorTy2020)
      Some(SBAResults(Some(sbaRatePrior2020),     Some(sbaRateAfter2020)))
    }else {
      Some(SBAResults(None, Some(SBARate(totalDaysCharged, dailyRateAfter2020, rateAfterTy2020))))
    }

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
