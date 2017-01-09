/*
 * Copyright 2017 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600e.v3

import play.api.libs.json.Format
import uk.gov.hmrc.ct.box.formats._


package object formats {

  implicit val B115Format: Format[B115] = new BooleanFormat[B115](B115.apply)

  implicit val E1Format: Format[E1] = new StringFormat[E1](E1.apply)

  implicit val E2Format: Format[E2] = new StringFormat[E2](E2.apply)

  implicit val E3Format: Format[E3] = new DateFormat[E3](E3.apply)

  implicit val E4Format: Format[E4] = new DateFormat[E4](E4.apply)

  implicit val E5Format: Format[E5] = new StringFormat[E5](E5.apply)

  implicit val E10Format: Format[E10] = new OptionalStringFormat[E10](E10.apply)

  implicit val E15Format: Format[E15] = new OptionalBooleanFormat[E15](E15.apply)

  implicit val E20Format: Format[E20] = new OptionalBooleanFormat[E20](E20.apply)

  implicit val E25Format: Format[E25] = new OptionalBooleanFormat[E25](E25.apply)

  implicit val E30Format: Format[E30] = new OptionalStringFormat[E30](E30.apply)

  implicit val E35Format: Format[E35] = new OptionalStringFormat[E35](E35.apply)

  implicit val E40Format: Format[E40] = new OptionalDateFormat[E40](E40.apply)

  implicit val E45Format: Format[E45] = new OptionalBooleanFormat[E45](E45.apply)

  implicit val E50Format: Format[E50] = new OptionalIntegerFormat[E50](E50.apply)

  implicit val E55Format: Format[E55] = new OptionalIntegerFormat[E55](E55.apply)

  implicit val E60Format: Format[E60] = new OptionalIntegerFormat[E60](E60.apply)

  implicit val E65Format: Format[E65] = new OptionalIntegerFormat[E65](E65.apply)

  implicit val E70Format: Format[E70] = new OptionalIntegerFormat[E70](E70.apply)

  implicit val E75Format: Format[E75] = new OptionalIntegerFormat[E75](E75.apply)

  implicit val E80Format: Format[E80] = new OptionalIntegerFormat[E80](E80.apply)

  implicit val E85Format: Format[E85] = new OptionalIntegerFormat[E85](E85.apply)

  implicit val E95Format: Format[E95] = new OptionalIntegerFormat[E95](E95.apply)

  implicit val E100Format: Format[E100] = new OptionalIntegerFormat[E100](E100.apply)

  implicit val E105Format: Format[E105] = new OptionalIntegerFormat[E105](E105.apply)

  implicit val E110Format: Format[E110] = new OptionalIntegerFormat[E110](E110.apply)

  implicit val E115Format: Format[E115] = new OptionalIntegerFormat[E115](E115.apply)

  implicit val E120Format: Format[E120] = new OptionalIntegerFormat[E120](E120.apply)

  implicit val E130Format: Format[E130] = new OptionalIntegerFormat[E130](E130.apply)

  implicit val E135Format: Format[E135] = new OptionalIntegerFormat[E135](E135.apply)

  implicit val E140Format: Format[E140] = new OptionalIntegerFormat[E140](E140.apply)

  implicit val E145Format: Format[E145] = new OptionalIntegerFormat[E145](E145.apply)

  implicit val E150Format: Format[E150] = new OptionalIntegerFormat[E150](E150.apply)

  implicit val E155Format: Format[E155] = new OptionalIntegerFormat[E155](E155.apply)

  implicit val E160Format: Format[E160] = new OptionalIntegerFormat[E160](E160.apply)

  implicit val E165Format: Format[E165] = new OptionalIntegerFormat[E165](E165.apply)

  implicit val E170AFormat: Format[E170A] = new OptionalIntegerFormat[E170A](E170A.apply)

  implicit val E170BFormat : Format[E170B] = new OptionalIntegerFormat[E170B](E170B.apply)

  implicit val E175Format: Format[E175] = new OptionalIntegerFormat[E175](E175.apply)

  implicit val E180Format: Format[E180] = new OptionalIntegerFormat[E180](E180.apply)

  implicit val E185Format: Format[E185] = new OptionalIntegerFormat[E185](E185.apply)

  implicit val E190Format: Format[E190] = new OptionalIntegerFormat[E190](E190.apply)
}
