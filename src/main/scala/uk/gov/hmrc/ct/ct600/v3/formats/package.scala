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

package uk.gov.hmrc.ct.ct600.v3

import play.api.libs.json.Format
import uk.gov.hmrc.ct.box.formats._
import uk.gov.hmrc.ct.ct600j.v3._

package object formats {

  implicit val b1Format: Format[B1] = new StringFormat[B1](B1.apply)

  implicit val B40Format: Format[B40] = new OptionalBooleanFormat[B40](B40.apply)

  implicit val B45Format: Format[B45Input] = new OptionalBooleanFormat[B45Input](B45Input.apply)

  implicit val B55Format: Format[B55] = new OptionalBooleanFormat[B55](B55.apply)

  implicit val B65Format: Format[B65] = new OptionalBooleanFormat[B65](B65.apply)

  implicit val B80AFormat: Format[B80A] = new OptionalBooleanFormat[B80A](B80A.apply)

  implicit val B85AFormat: Format[B85A] = new OptionalBooleanFormat[B85A](B85A.apply)

  implicit val B90AFormat: Format[B90A] = new OptionalStringFormat[B90A](B90A.apply)

  implicit val B140Format: Format[B140] = new OptionalBooleanFormat[B140](B140.apply)

  implicit val B145Format: Format[B145] = new OptionalIntegerFormat[B145](B145.apply)

  implicit val B150Format: Format[B150] = new OptionalBooleanFormat[B150](B150.apply)

  implicit val B155Format: Format[B155] = new IntegerFormat[B155](B155.apply)

  implicit val B160Format: Format[B160] = new OptionalIntegerFormat[B160](B160.apply)

  implicit val B165Format: Format[B165] = new IntegerFormat[B165](B165.apply)

  implicit val B170Format: Format[B170] = new IntegerFormat[B170](B170.apply)

  implicit val B190Format: Format[B190] = new IntegerFormat[B190](B190.apply)

  implicit val B205Format: Format[B205] = new OptionalIntegerFormat[B205](B205.apply)

  implicit val B235Format: Format[B235] = new IntegerFormat[B235](B235.apply)

  implicit val B275Format: Format[B275] = new IntegerFormat[B275](B275.apply)

  implicit val B280Format: Format[B280] = new BooleanFormat[B280](B280.apply)

  implicit val B295Format: Format[B295] = new IntegerFormat[B295](B295.apply)

  implicit val B300Format: Format[B300] = new IntegerFormat[B300](B300.apply)

  implicit val B305Format: Format[B305] = new IntegerFormat[B305](B305.apply)

  implicit val B315Format: Format[B315] = new IntegerFormat[B315](B315.apply)

  implicit val B335Format: Format[B335] = new IntegerFormat[B335](B335.apply)

  implicit val B485Format: Format[B485] = new BooleanFormat[B485](B485.apply)

  implicit val BFQ1Format: Format[BFQ1] = new OptionalBooleanFormat[BFQ1](BFQ1.apply)

  implicit val B620Format: Format[B620] = new OptionalIntegerFormat[B620](B620.apply)

  implicit val B515Format: Format[B515] = new OptionalBigDecimalFormat[B515](B515.apply)

  implicit val B527Format: Format[B527] = new OptionalBigDecimalFormat[B527](B527.apply)

  implicit val B595Format: Format[B595] = new OptionalBigDecimalFormat[B595](B595.apply)

  implicit val B705Format: Format[B705] = new OptionalIntegerFormat[B705](B705.apply)

  implicit val B710Format: Format[B710] = new OptionalIntegerFormat[B710](B710.apply)

  implicit val B735Format: Format[B735] = new OptionalIntegerFormat[B735](B735.apply)

  implicit val B750Format: Format[B750] = new OptionalIntegerFormat[B750](B750.apply)

  implicit val B755Format: Format[B755] = new OptionalIntegerFormat[B755](B755.apply)

  implicit val B760Format: Format[B760] = new IntegerFormat[B760](B760.apply)

  implicit val B765Format: Format[B765] = new IntegerFormat[B765](B765.apply)

  implicit val B775Format: Format[B775] = new IntegerFormat[B775](B775.apply)

  implicit val B780Format: Format[B780] = new IntegerFormat[B780](B780.apply)

  implicit val B860Format: Format[B860] = new OptionalIntegerFormat[B860](B860.apply)

  implicit val B920Format: Format[B920] = new StringFormat[B920](B920.apply)

  implicit val PAYEEQ1Format: Format[PAYEEQ1] = new OptionalBooleanFormat[PAYEEQ1](PAYEEQ1.apply)

  implicit val REPAYMENTSQ1Format: Format[REPAYMENTSQ1] = new OptionalBooleanFormat[REPAYMENTSQ1](REPAYMENTSQ1.apply)

  implicit val B925Format: Format[B925] = new StringFormat[B925](B925.apply)

  implicit val B930Format: Format[B930] = new StringFormat[B930](B930.apply)

  implicit val B935Format: Format[B935] = new StringFormat[B935](B935.apply)

  implicit val B940Format: Format[B940] = new OptionalStringFormat[B940](B940.apply)

  implicit val B945Format: Format[B945] = new OptionalStringFormat[B945](B945.apply)

  implicit val B955Format: Format[B955] = new OptionalStringFormat[B955](B955.apply)

  implicit val B960Format: Format[B960] = new OptionalStringFormat[B960](B960.apply)

  implicit val B960_1Format: Format[B960_1] = new OptionalStringFormat[B960_1](B960_1.apply)

  implicit val B960_2Format: Format[B960_2] = new OptionalStringFormat[B960_2](B960_2.apply)

  implicit val B960_3Format: Format[B960_3] = new OptionalStringFormat[B960_3](B960_3.apply)

  implicit val B960_5Format: Format[B960_5] = new OptionalStringFormat[B960_5](B960_5.apply)

  implicit val B965Format: Format[B965] = new OptionalStringFormat[B965](B965.apply)

  implicit val B970Format: Format[B970] = new OptionalStringFormat[B970](B970.apply)

  implicit val B975Format: Format[B975] = new OptionalStringFormat[B975](B975.apply)

  implicit val B980Format: Format[B980] = new OptionalDateFormat[B980](B980.apply)

  implicit val B985Format: Format[B985] = new OptionalStringFormat[B985](B985.apply)

  implicit val N092Format: Format[N092] = new OptionalBooleanFormat[N092](N092.apply)

}
