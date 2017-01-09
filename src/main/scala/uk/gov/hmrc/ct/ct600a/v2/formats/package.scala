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

package uk.gov.hmrc.ct.ct600a.v2

import play.api.libs.json.{Format, Json}
import uk.gov.hmrc.ct.box.formats._

package object formats {

  implicit val A1Format: Format[A1] = new OptionalBooleanFormat[A1](A1.apply)

  implicit val a2Format: Format[A2] = new OptionalIntegerFormat[A2](A2.apply)

  implicit val a3Format: Format[A3] = new OptionalBigDecimalFormat[A3](A3.apply)

  implicit val a4Format: Format[A4] = new OptionalIntegerFormat[A4](A4.apply)

  implicit val a5Format: Format[A5] = new OptionalIntegerFormat[A5](A5.apply)

  implicit val a6Format: Format[A6] = new OptionalIntegerFormat[A6](A6.apply)

  implicit val a7Format: Format[A7] = new OptionalBigDecimalFormat[A7](A7.apply)

  implicit val a8Format: Format[A8] = new OptionalIntegerFormat[A8](A8.apply)

  implicit val A8InverseFormat: Format[A8Inverse] = new OptionalIntegerFormat[A8Inverse](A8Inverse.apply)

  implicit val a9Format: Format[A9] = new OptionalIntegerFormat[A9](A9.apply)

  implicit val A9InverseFormat: Format[A9Inverse] = new OptionalIntegerFormat[A9Inverse](A9Inverse.apply)

  implicit val a10Format: Format[A10] = new OptionalIntegerFormat[A10](A10.apply)

  implicit val A10InverseFormat: Format[A10Inverse] = new OptionalIntegerFormat[A10Inverse](A10Inverse.apply)

  implicit val a11Format: Format[A11] = new OptionalBigDecimalFormat[A11](A11.apply)

  implicit val A11InverseFormat: Format[A11Inverse] = new OptionalBigDecimalFormat[A11Inverse](A11Inverse.apply)

  implicit val a12Format: Format[A12] = new OptionalIntegerFormat[A12](A12.apply)

  implicit val a13Format: Format[A13] = new OptionalBigDecimalFormat[A13](A13.apply)

  implicit val loanFormatter = Json.format[Loan]

  implicit val lp02Format: Format[LP02] = Json.format[LP02]

  implicit val writeOffFormatter = Json.format[WriteOff]

  implicit val lp03Format: Format[LP03] = Json.format[LP03]

  implicit val lp04Format: Format[LP04] = new OptionalIntegerFormat[LP04](LP04.apply)

  implicit val lpq01Format: Format[LPQ01] = new BooleanFormat[LPQ01](LPQ01.apply)

  implicit val lpq03Format: Format[LPQ03] = new OptionalBooleanFormat[LPQ03](LPQ03.apply)

  implicit val lpq04Format: Format[LPQ04] = new OptionalBooleanFormat[LPQ04](LPQ04.apply)

  implicit val lpq05Format: Format[LPQ05] = new OptionalBooleanFormat[LPQ05](LPQ05.apply)

  implicit val lpq06Format: Format[LPQ06] = new OptionalBooleanFormat[LPQ06](LPQ06.apply)

  implicit val lpq07Format: Format[LPQ07] = new OptionalDateFormat[LPQ07](LPQ07.apply)

  implicit val lpq08Format: Format[LPQ08] = new OptionalBooleanFormat[LPQ08](LPQ08.apply)

  implicit val lpq09Format: Format[LPQ09] = new OptionalBooleanFormat[LPQ09](LPQ09.apply)

  implicit val lpq10Format: Format[LPQ10] = new OptionalBooleanFormat[LPQ10](LPQ10.apply)
}
