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
import uk.gov.hmrc.ct.ct600.NumberRounding
import uk.gov.hmrc.ct.ct600.calculations.{AccountingPeriodHelper, TaxYear}


case class SbaRate(numberOfDaysRate: Int, dailyRate: BigDecimal) extends NumberRounding{
  val costRate  = roundedToIntHalfUp(numberOfDaysRate * dailyRate)
}

case class SbaResults(rateOne: SbaRate, rateTwo: Option[SbaRate] = None) extends NumberRounding{
  println("rateOne.costRate  " + rateOne.costRate )
  println("rateTwo.costRate  " + rateTwo.map(_.costRate))
  val totalCost:Option[Int] = Some(roundedToIntHalfUp(rateOne.costRate + rateTwo.map(_.costRate).getOrElse(0)))
}

trait SBACalculator extends NumberRounding with AccountingPeriodHelper {


  val ratePriorTy2020: BigDecimal = 0.02
  val rateAfterTy2020: BigDecimal = 0.03
  //maybe create case class from year to tax rate like in ct in case this happens again
  //rate to year


  def getDaysIntheYear(apStartDate: LocalDate) = {
    val yearAfterApStart = apStartDate.plusYears(1)
    if (apStartDate.getDayOfMonth == yearAfterApStart.getDayOfMonth) daysBetween(apStartDate, yearAfterApStart) - 1 else 366
  }

  def apportionedCostOfBuilding(cost: BigDecimal, daysInTheYear: Int, rateForTy: BigDecimal): BigDecimal = (cost * rateForTy) / daysInTheYear

  def isEarliestWrittenContractAfterAPStart(contractDate: LocalDate, apStartDate: LocalDate): Boolean = contractDate.isAfter(apStartDate)

  /* This is the 2% rounded up*/
  def getSBADetails(apStartDate: LocalDate, apEndDate: LocalDate, maybeFirstUsageDate: Option[LocalDate], maybeCost: Option[Int]): Option[SbaResults] = {
    println("apStartDate " + apStartDate)
    println("apEndDate " + apEndDate)
    println("maybeFirstUsageDate " + maybeFirstUsageDate)
    //put it new
    (maybeFirstUsageDate, maybeCost) match {
      case (Some(firstUsageDate), Some(cost)) => {
        //need to split this information up
        val daysInTheYear = getDaysIntheYear(apStartDate)
        //todo what about maybeFirstUsageDate?
        //get days for 2% tax rate
        //get days for 3% tax rate
        val isAfterTy2020 = if (financialYearForDate(apStartDate) >= 2020 && financialYearForDate(apEndDate) >= 2020) true else false

        val dailyRateAfter2020 = apportionedCostOfBuilding(cost, daysInTheYear, rateAfterTy2020)
        val dailyRateBefore2020 = apportionedCostOfBuilding(cost, daysInTheYear, ratePriorTy2020)


        val sbaResult: Option[SbaResults] = if (isEarliestWrittenContractAfterAPStart(firstUsageDate, apStartDate)) {
          println("I have been called " + isEarliestWrittenContractAfterAPStart(firstUsageDate, apStartDate))
          //todo frist
          val daysToApplyRate =  daysBetween(firstUsageDate, apEndDate)
          Some(SbaResults(rateOne = SbaRate(daysToApplyRate, dailyRateBefore2020)))
        } else
          {
            if (isAfterTy2020) dealWith2020Logic(apStartDate, apEndDate, dailyRateAfter2020, dailyRateBefore2020)
             else {
              println("daysBetween(apStartDate, apEndDate " + daysBetween(apStartDate, apEndDate))
               Some(SbaResults(rateOne = SbaRate(daysBetween(apStartDate, apEndDate), dailyRateBefore2020)))
             }
          }
        sbaResult
      }
      case _ => None
    }
  }

  private def dealWith2020Logic(apStartDate: LocalDate, apEndDate: LocalDate, dailyRateAfter2020: BigDecimal, dailyRateBefore2020: BigDecimal): Option[SbaResults] = {
    val daysBeforeTy2020: Int = daysBetween(apStartDate, LocalDate.parse("2020-04-01"))

    println("daysBefore2020 " + daysBeforeTy2020)

    val totalDaysInAccountingPeriod: Int = daysBetween(apStartDate, apEndDate)


    val daysAfter2020 = totalDaysInAccountingPeriod - daysBeforeTy2020


    Some(SbaResults(rateOne = SbaRate(daysBeforeTy2020, ratePriorTy2020), rateTwo = Some(SbaRate(daysAfter2020, rateAfterTy2020))))
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
