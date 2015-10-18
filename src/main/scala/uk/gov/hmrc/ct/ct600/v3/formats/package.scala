/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600.v3

import play.api.libs.json.Format
import uk.gov.hmrc.ct.box.formats._

package object formats {

  implicit val b1Format: Format[B1] = new StringFormat[B1](B1.apply)

  implicit val b2Format: Format[B2] = new StringFormat[B2](B2.apply)

  implicit val b3Format: Format[B3] = new StringFormat[B3](B3.apply)

  implicit val B4Format: Format[B4] = new StringFormat[B4](B4.apply)

  implicit val B40Format: Format[B40] = new OptionalBooleanFormat[B40](B40.apply)

  implicit val B45Format: Format[B45] = new OptionalBooleanFormat[B45](B45.apply)

  implicit val B55Format: Format[B55] = new OptionalBooleanFormat[B55](B55.apply)

  implicit val B335Format: Format[B335] = new IntegerFormat[B335](B335.apply)

  implicit val BFQ1Format: Format[BFQ1] = new OptionalBooleanFormat[BFQ1](BFQ1.apply)

  implicit val B620Format: Format[B620] = new OptionalIntegerFormat[B620](B620.apply)

  implicit val B515Format: Format[B515] = new BigDecimalFormat[B515](B515.apply)

  implicit val B595Format: Format[B595] = new BigDecimalFormat[B595](B595.apply)

  implicit val B860Format: Format[B860] = new OptionalIntegerFormat[B860](B860.apply)

  implicit val B920Format: Format[B920] = new StringFormat[B920](B920.apply)

  implicit val PAYEEQ1Format: Format[PAYEEQ1] = new OptionalBooleanFormat[PAYEEQ1](PAYEEQ1.apply)

  implicit val B925Format: Format[B925] = new StringFormat[B925](B925.apply)

  implicit val B930Format: Format[B930] = new StringFormat[B930](B930.apply)

  implicit val B935Format: Format[B935] = new StringFormat[B935](B935.apply)

  implicit val B940Format: Format[B940] = new OptionalStringFormat[B940](B940.apply)

  implicit val B955Format: Format[B955] = new OptionalStringFormat[B955](B955.apply)

  implicit val B960Format: Format[B960] = new OptionalStringFormat[B960](B960.apply)

  implicit val B965Format: Format[B965] = new OptionalStringFormat[B965](B965.apply)
}
