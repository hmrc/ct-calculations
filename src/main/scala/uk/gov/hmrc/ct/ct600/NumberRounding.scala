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

package uk.gov.hmrc.ct.ct600

trait NumberRounding {

  def roundedToIntHalfUp(value: BigDecimal): Int = {
    value.setScale(0, BigDecimal.RoundingMode.HALF_UP).toInt
  }

  def roundedToIntHalfDown(value: BigDecimal): Int = {
    value.setScale(0, BigDecimal.RoundingMode.HALF_DOWN).toInt
  }

  def roundedTwoDecimalPlaces(value: BigDecimal): BigDecimal = {
    value.setScale(2, BigDecimal.RoundingMode.HALF_UP)
  }

  def roundedToInt(value: BigDecimal): Int = {
    value.setScale(0, BigDecimal.RoundingMode.HALF_UP).toInt
  }

}
