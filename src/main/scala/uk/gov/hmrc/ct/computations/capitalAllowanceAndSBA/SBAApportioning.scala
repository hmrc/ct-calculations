/*
 * Copyright 2022 HM Revenue & Customs
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


case class SbaRate(numberOfDaysRate: Int, dailyRate: BigDecimal, rateYearlyPercentage: BigDecimal) extends NumberRounding {
  val rateYearlyPercentageAsInt = roundedToInt(rateYearlyPercentage * 100)
  val costRate = roundedToIntHalfUp(numberOfDaysRate * dailyRate)
}

case class SbaResults(ratePriorTaxYear2020: Option[SbaRate] = None, ratePostTaxYear2020: Option[SbaRate] = None) extends NumberRounding {
  val maybeTotalCodeBefore2020 =   ratePriorTaxYear2020.map(_.costRate).getOrElse(0)
  val maybeTotalCodeAfter2020 =   ratePostTaxYear2020.map(_.costRate).getOrElse(0)
  val totalCost: Option[Int] = Some(maybeTotalCodeBefore2020 + maybeTotalCodeAfter2020)
}
