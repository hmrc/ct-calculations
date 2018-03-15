/*
 * Copyright 2018 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600.v3.calculations

import uk.gov.hmrc.ct.box.CtTypeConverters
import uk.gov.hmrc.ct.computations.CP291
import uk.gov.hmrc.ct.ct600.v3._

trait B325Calculator extends CtTypeConverters {

  def calculateB325(b350: B350, b400: B400, b315: B315, b330: B330, b380: B380, cp291: CP291): B325 = {

    val ZERO = Some(0)

    val value =
      if (!b330.isPositive) None
      else b380 match {
        case noTradingProfit if cp291.noValue => ZERO
        case singleFinancialYear if singleFinancialYear.noValue =>
          if (b350.value <= b315.value) Some(b350.value)
          else ZERO
        case twoFinancialYears if twoFinancialYears.hasValue =>
          if ((b350.value + b400.value) <= b315.value) Some(b350.value + b400.value)
          else ZERO
      }

    B325(value)
  }
}
