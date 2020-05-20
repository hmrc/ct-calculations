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

package uk.gov.hmrc.ct.computations.capitalAllowanceAndSBA

import uk.gov.hmrc.ct.ct600.NumberRounding

case class SBARate(numberOfDaysRate: Int, dailyRate: BigDecimal, rateYearlyPercentage: BigDecimal) extends NumberRounding {

  val costRate = roundedToIntHalfUp(numberOfDaysRate * dailyRate)
}

case class SBAResults(ratePriorTaxYear2020: SBARate, ratePostTaxYear2020: Option[SBARate] = None) extends NumberRounding {

  val totalCost: Option[Int] = Some(ratePriorTaxYear2020.costRate + ratePostTaxYear2020.map(_.costRate).getOrElse(0))
}
