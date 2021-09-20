/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.ct.utils

import uk.gov.hmrc.ct.box.ValidatableBox._
object CatoInputBounds {

  val turnoverHMRCMaxValue632k: Int = 632000
  val turnoverCOHOMaxValue10m: Int = 10200000
  val minimumValue0: Int = 0
  val oldMaxValue99999999: Int = 99999999
  val oldMinValue99999999: Int = -99999999
  val minimumValueAsString: String = minimumValue0.toString
  val turnoverHMRCMaxWithCommas: String = commaForThousands(turnoverHMRCMaxValue632k)
  val turnoverCOHOMaxWithCommas: String = commaForThousands(turnoverCOHOMaxValue10m)
  val oldMaxWithCommas: String = commaForThousands(oldMaxValue99999999)
  val oldMinWithCommas: String = commaForThousands(oldMinValue99999999)
}
