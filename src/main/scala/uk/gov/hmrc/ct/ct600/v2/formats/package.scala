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

package uk.gov.hmrc.ct.ct600.v2

import play.api.libs.json.Format
import uk.gov.hmrc.ct.box.formats._

package object formats {

  implicit val b1Format: Format[B1] = new IntegerFormat[B1](B1.apply)

  implicit val b2Format: Format[B2] = new OptionalBooleanFormat[B2](B2.apply)

  implicit val b3Format: Format[B3] = new IntegerFormat[B3](B3.apply)

  implicit val B4Format: Format[B4] = new OptionalIntegerFormat[B4](B4.apply)

  implicit val B5Format: Format[B5] = new IntegerFormat[B5](B5.apply)

  implicit val B6Format: Format[B6] = new IntegerFormat[B6](B6.apply)

  implicit val b8Format: Format[B8] = new IntegerFormat[B8](B8.apply)

  implicit val b9Format: Format[B9] = new IntegerFormat[B9](B9.apply)

  implicit val b10Format: Format[B10] = new IntegerFormat[B10](B10.apply)

  implicit val B11Format: Format[B11] = new IntegerFormat[B11](B11.apply)

  implicit val b12Format: Format[B12] = new IntegerFormat[B12](B12.apply)

  implicit val b13Format: Format[B13] = new IntegerFormat[B13](B13.apply)

  implicit val B14Format: Format[B14] = new OptionalIntegerFormat[B14](B14.apply)

  implicit val b15Format: Format[B15] = new IntegerFormat[B15](B15.apply)

  implicit val b16Format: Format[B16] = new IntegerFormat[B16](B16.apply)

  implicit val b17Format: Format[B17] = new IntegerFormat[B17](B17.apply)

  implicit val B18Format: Format[B18] = new IntegerFormat[B18](B18.apply)

  implicit val b19Format: Format[B19] = new IntegerFormat[B19](B19.apply)

  implicit val b20Format: Format[B20] = new IntegerFormat[B20](B20.apply)

  implicit val b21Format: Format[B21] = new IntegerFormat[B21](B21.apply)

  implicit val B30Format: Format[B30] = new IntegerFormat[B30](B30.apply)

  implicit val b31Format: Format[B31] = new OptionalBooleanFormat[B31](B31.apply)

  implicit val B35Format: Format[B35] = new IntegerFormat[B35](B35.apply)

  implicit val B37Format: Format[B37] = new IntegerFormat[B37](B37.apply)

  implicit val b38Format: Format[B38] = new OptionalIntegerFormat[B38](B38.apply)

  implicit val b39Format: Format[B39] = new OptionalIntegerFormat[B39](B39.apply)

  implicit val b42aFormat: Format[B42a] = new OptionalBooleanFormat[B42a](B42a.apply)

  implicit val b42bFormat: Format[B42b] = new OptionalBooleanFormat[B42b](B42b.apply)

  implicit val b43Format: Format[B43] = new IntegerFormat[B43](B43.apply)

  implicit val b44Format: Format[B44] = new IntegerFormat[B44](B44.apply)

  implicit val b45Format: Format[B45] = new BigDecimalFormat[B45](B45.apply)

  implicit val b46Format: Format[B46] = new BigDecimalFormat[B46](B46.apply)

  implicit val b46RFormat: Format[B46R] = new IntegerFormat[B46R](B46R.apply)

  implicit val b53Format: Format[B53] = new OptionalIntegerFormat[B53](B53.apply)

  implicit val b54Format: Format[B54] = new IntegerFormat[B54](B54.apply)

  implicit val b55Format: Format[B55] = new BigDecimalFormat[B55](B55.apply)

  implicit val b56Format: Format[B56] = new BigDecimalFormat[B56](B56.apply)

  implicit val b56RFormat: Format[B56R] = new IntegerFormat[B56R](B56R.apply)

  implicit val b63Format: Format[B63] = new BigDecimalFormat[B63](B63.apply)

  implicit val b64Format: Format[B64] = new BigDecimalFormat[B64](B64.apply)

  implicit val b65Format: Format[B65] = new BigDecimalFormat[B65](B65.apply)

  implicit val b70Format: Format[B70] = new BigDecimalFormat[B70](B70.apply)

  implicit val b78Format: Format[B78] = new BigDecimalFormat[B78](B78.apply)

  implicit val b79Format: Format[B79] = new OptionalBigDecimalFormat[B79](B79.apply)

  implicit val b80Format: Format[B80] = new OptionalBooleanFormat[B80](B80.apply)

  implicit val b84Format: Format[B84] = new BigDecimalFormat[B84](B84.apply)

  implicit val b85Format: Format[B85] = new BigDecimalFormat[B85](B85.apply)

  implicit val b86Format: Format[B86] = new BigDecimalFormat[B86](B86.apply)

  implicit val b91Format: Format[B91] = new BigDecimalFormat[B91](B91.apply)

  implicit val b92Format: Format[B92] = new BigDecimalFormat[B92](B92.apply)

  implicit val b93Format: Format[B93] = new BigDecimalFormat[B93](B93.apply)

  implicit val b105Format: Format[B105] = new OptionalIntegerFormat[B105](B105.apply)

  implicit val b106Format: Format[B106] = new OptionalIntegerFormat[B106](B106.apply)

  implicit val b107Format: Format[B107] = new OptionalIntegerFormat[B107](B107.apply)

  implicit val b108Format: Format[B108] = new OptionalIntegerFormat[B108](B108.apply)

  implicit val b118Format: Format[B118] = new IntegerFormat[B118](B118.apply)

  implicit val b121Format: Format[B121] = new IntegerFormat[B121](B121.apply)

  implicit val b122Format: Format[B122] = new OptionalIntegerFormat[B122](B122.apply)

  implicit val b139Format: Format[B139] = new BooleanFormat[B139](B139.apply)

  implicit val b140Format: Format[B140] = new OptionalIntegerFormat[B140](B140.apply)

  implicit val b149Format: Format[B149] = new OptionalStringFormat[B149](B149.apply)

  implicit val b150Format: Format[B150] = new OptionalStringFormat[B150](B150.apply)

  implicit val b151Format: Format[B151] = new OptionalStringFormat[B151](B151.apply)

  implicit val b152Format: Format[B152] = new OptionalStringFormat[B152](B152.apply)

  implicit val b156Format: Format[B156] = new OptionalStringFormat[B156](B156.apply)

  implicit val b158Format: Format[B158] = new OptionalStringFormat[B158](B158.apply)

  implicit val b1571Format: Format[B1571] = new OptionalStringFormat[B1571](B1571.apply)

  implicit val b1572Format: Format[B1572] = new OptionalStringFormat[B1572](B1572.apply)

  implicit val b1573Format: Format[B1573] = new OptionalStringFormat[B1573](B1573.apply)

  implicit val b1574Format: Format[B1574] = new OptionalStringFormat[B1574](B1574.apply)

  implicit val b1575Format: Format[B1575] = new OptionalStringFormat[B1575](B1575.apply)

  implicit val b172Format: Format[B172] = new OptionalIntegerFormat[B172](B172.apply)

  implicit val b174Format: Format[B174] = new IntegerFormat[B174](B174.apply)

  implicit val b153Format: Format[B153] = new OptionalStringFormat[B153](B153.apply)

  implicit val b155Format: Format[B155] = new StringFormat[B155](B155.apply)

  implicit val RDQ1Format: Format[RDQ1] = new BooleanFormat[RDQ1](RDQ1.apply)

  implicit val RDQ2Format: Format[RDQ2] = new BooleanFormat[RDQ2](RDQ2.apply)

  implicit val RSQ1Format: Format[RSQ1] = new OptionalBooleanFormat[RSQ1](RSQ1.apply)

  implicit val RSQ2Format: Format[RSQ2] = new OptionalBooleanFormat[RSQ2](RSQ2.apply)

  implicit val RSQ4Format: Format[RSQ4] = new OptionalBooleanFormat[RSQ4](RSQ4.apply)

  implicit val RSQ7Format: Format[RSQ7] = new OptionalBooleanFormat[RSQ7](RSQ7.apply)

  implicit val RSQ8Format: Format[RSQ8] = new OptionalBooleanFormat[RSQ8](RSQ8.apply)

  implicit val B1000Format: Format[B1000] = new StringFormat[B1000](B1000.apply)
}
