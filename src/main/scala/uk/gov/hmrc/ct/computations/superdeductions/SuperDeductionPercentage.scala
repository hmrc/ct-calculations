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

package uk.gov.hmrc.ct.computations.superdeductions

import org.joda.time.Days
import uk.gov.hmrc.ct.computations.HmrcAccountingPeriod
import uk.gov.hmrc.ct.utils.DateImplicits.DateOperators


case class SuperDeductionPercentage(percentage: BigDecimal)



object SuperDeductionPercentage {

  def apply(hmrcAccountingPeriod: HmrcAccountingPeriod, superDeductionPeriod: SuperDeductionPeriod): SuperDeductionPercentage = {
    val percentage:Double = if (hmrcAccountingPeriod.end.value.isBefore(superDeductionPeriod.start.value)) {
      0
    } else if (hmrcAccountingPeriod.end.value <= superDeductionPeriod.end.value) {
      1.3
    } else {
      val overlapStart = if (hmrcAccountingPeriod.start.value <= superDeductionPeriod.start.value) superDeductionPeriod.start.value else hmrcAccountingPeriod.start.value
      val overlapEnd = if (hmrcAccountingPeriod.end.value <= superDeductionPeriod.end.value) hmrcAccountingPeriod.end.value else superDeductionPeriod.end.value
      val daysOverlap = Days.daysBetween(overlapStart, overlapEnd).getDays + 1
      ((daysOverlap.toDouble / hmrcAccountingPeriod.noOfDaysInAccountingPeriod.toDouble) * 0.3) + 1
    }
    val str: String = "%.5f".format(percentage)
    SuperDeductionPercentage(BigDecimal(str))
  }

}

