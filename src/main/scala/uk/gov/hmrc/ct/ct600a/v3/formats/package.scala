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

package uk.gov.hmrc.ct.ct600a.v3

import play.api.libs.json.{Format, Json}
import uk.gov.hmrc.ct.box.formats._


package object formats {

//  implicit val A1Format: Format[A1] = new OptionalBooleanFormat[A1](A1.apply)

  implicit val a5Format: Format[A5] = new OptionalBooleanFormat[A5](A5.apply)

  implicit val a15Format: Format[A15] = new OptionalIntegerFormat[A15](A15.apply)

  implicit val a20Format: Format[A20] = new OptionalBigDecimalFormat[A20](A20.apply)

  implicit val a30Format: Format[A30] = new OptionalIntegerFormat[A30](A30.apply)

  implicit val a35Format: Format[A35] = new OptionalIntegerFormat[A35](A35.apply)

  implicit val a40Format: Format[A40] = new OptionalIntegerFormat[A40](A40.apply)

  implicit val a45Format: Format[A45] = new OptionalBigDecimalFormat[A45](A45.apply)

  implicit val a55Format: Format[A55] = new OptionalIntegerFormat[A55](A55.apply)

  implicit val a55InverseFormat: Format[A55Inverse] = new OptionalIntegerFormat[A55Inverse](A55Inverse.apply)

  implicit val a60Format: Format[A60] = new OptionalIntegerFormat[A60](A60.apply)

  implicit val a60InverseFormat: Format[A60Inverse] = new OptionalIntegerFormat[A60Inverse](A60Inverse.apply)

  implicit val a65Format: Format[A65] = new OptionalIntegerFormat[A65](A65.apply)

  implicit val a65InverseFormat: Format[A65Inverse] = new OptionalIntegerFormat[A65Inverse](A65Inverse.apply)

  implicit val a70Format: Format[A70] = new OptionalBigDecimalFormat[A70](A70.apply)

  implicit val a70InverseFormat: Format[A70Inverse] = new OptionalBigDecimalFormat[A70Inverse](A70Inverse.apply)

  implicit val a75Format: Format[A75] = new OptionalIntegerFormat[A75](A75.apply)

  implicit val a80Format: Format[A80] = new OptionalBigDecimalFormat[A80](A80.apply)

  implicit val repaymentFormat = Json.format[Repayment]

  implicit val writeOffFormat = Json.format[WriteOff]

  implicit val loanFormat = Json.format[Loan]

  implicit val loansToParticipatorsFormat: Format[LoansToParticipators] = Json.format[LoansToParticipators]

  implicit val lp04Format: Format[LP04] = new OptionalIntegerFormat[LP04](LP04.apply)

  implicit val lpq01Format: Format[LPQ01] = new BooleanFormat[LPQ01](LPQ01.apply)

  implicit val lpq03Format: Format[LPQ03] = new OptionalBooleanFormat[LPQ03](LPQ03.apply)

  implicit val lpq04Format: Format[LPQ04] = new OptionalBooleanFormat[LPQ04](LPQ04.apply)

  implicit val lpq07Format: Format[LPQ07] = new OptionalDateFormat[LPQ07](LPQ07.apply)

  implicit val lpq08Format: Format[LPQ08] = new OptionalBooleanFormat[LPQ08](LPQ08.apply)

  implicit val lpq10Format: Format[LPQ10] = new OptionalBooleanFormat[LPQ10](LPQ10.apply)

}
