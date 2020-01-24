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
import uk.gov.hmrc.ct.ct600.calculations.AccountingPeriodHelper

trait SBACalculator extends NumberRounding with AccountingPeriodHelper {


  def leapYearDateCutOff(localDate: LocalDate) = new LocalDate(s"${localDate.getYear}-03-01")

  val rate: BigDecimal = 0.02

  def getDaysIntheYear(apStartDate: LocalDate) = if (apStartDate.year().isLeap && apStartDate.isBefore(leapYearDateCutOff(apStartDate))) 366 else 365

  def apportionedCostOfBuilding(cost: BigDecimal, daysInTheYear: Int): BigDecimal = (cost * rate) / daysInTheYear

  def isEarliestWrittenContractAfterAPStart(contractDate: LocalDate, apStartDate: LocalDate): Boolean = contractDate.isAfter(apStartDate)

  /* This is the 2% rounded up*/
  def getAmountClaimableForSBA(apStartDate: LocalDate, apEndDate: LocalDate, contract: Option[LocalDate], firstUsage: Option[LocalDate], cost: Option[BigDecimal]): Option[Int] = {

    val daysInTheYear = getDaysIntheYear(apStartDate)

    (contract, firstUsage, cost) match {
      case (Some(contractDate), Some(firstUsageDate), Some(maybeCost)) => {
        val dailyRate = apportionedCostOfBuilding(maybeCost, daysInTheYear)
        val firstClaimableDate = if(contractDate.isBefore(firstUsageDate)) contractDate else firstUsageDate

        val totalCost = if (isEarliestWrittenContractAfterAPStart(firstClaimableDate, apStartDate)) {
          daysBetween(firstClaimableDate, apEndDate) * dailyRate
        } else {
          daysBetween(apStartDate, apEndDate) * dailyRate
        }

        Some(roundedToIntHalfUp(totalCost))

      }
      case _ => None
    }

  }

  //we can also use cats to make this look better.
  def sumAmount(xs:List[Option[Int]]) = {
    xs.fold(Option(0)){
      (accumulator, element) =>
        accumulator.flatMap(accum => element.map(ele => accum + ele))
    }
  }
}
