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

package uk.gov.hmrc.ct.ct600e.v2

import play.api.libs.json.Format
import uk.gov.hmrc.ct.box.formats._


package object formats {

  implicit val E1Format: Format[E1] = new OptionalIntegerFormat[E1](E1.apply)

  implicit val E2Format: Format[E2] = new OptionalIntegerFormat[E2](E2.apply)

  implicit val E3Format: Format[E3] = new OptionalIntegerFormat[E3](E3.apply)

  implicit val E4Format: Format[E4] = new OptionalIntegerFormat[E4](E4.apply)

  implicit val E5Format: Format[E5] = new OptionalIntegerFormat[E5](E5.apply)

  implicit val E6Format: Format[E6] = new OptionalIntegerFormat[E6](E6.apply)

  implicit val E7Format: Format[E7] = new OptionalIntegerFormat[E7](E7.apply)

  implicit val E8Format: Format[E8] = new OptionalIntegerFormat[E8](E8.apply)

  implicit val E9Format: Format[E9] = new OptionalIntegerFormat[E9](E9.apply)

  implicit val E10Format: Format[E10] = new OptionalIntegerFormat[E10](E10.apply)

  implicit val E11Format: Format[E11] = new OptionalIntegerFormat[E11](E11.apply)

  implicit val E12Format: Format[E12] = new OptionalIntegerFormat[E12](E12.apply)

  implicit val E13Format: Format[E13] = new OptionalIntegerFormat[E13](E13.apply)

  implicit val E14Format: Format[E14] = new OptionalIntegerFormat[E14](E14.apply)

  implicit val E15Format: Format[E15] = new OptionalIntegerFormat[E15](E15.apply)

  implicit val E16Format: Format[E16] = new OptionalIntegerFormat[E16](E16.apply)

  implicit val E17Format: Format[E17] = new OptionalIntegerFormat[E17](E17.apply)

  implicit val E18Format: Format[E18] = new OptionalIntegerFormat[E18](E18.apply)

  implicit val E19Format: Format[E19] = new OptionalIntegerFormat[E19](E19.apply)

  implicit val E20Format: Format[E20] = new OptionalIntegerFormat[E20](E20.apply)

  implicit val E20aFormat: Format[E20a] = new OptionalIntegerFormat[E20a](E20a.apply)

  implicit val E21Format: Format[E21] = new OptionalIntegerFormat[E21](E21.apply)

  implicit val E21bFormat: Format[E21b] = new OptionalIntegerFormat[E21b](E21b.apply)

  implicit val E22Format: Format[E22] = new OptionalIntegerFormat[E22](E22.apply)

  implicit val E22cFormat: Format[E22c] = new OptionalIntegerFormat[E22c](E22c.apply)

  implicit val E23Format: Format[E23] = new OptionalIntegerFormat[E23](E23.apply)

  implicit val E23dFormat: Format[E23d] = new OptionalIntegerFormat[E23d](E23d.apply)

  implicit val E24eFormat: Format[E24e] = new OptionalIntegerFormat[E24e](E24e.apply)

  implicit val E24e1Format: Format[E24eA] = new OptionalIntegerFormat[E24eA](E24eA.apply)

  implicit val E24e2Format: Format[E24eB] = new OptionalIntegerFormat[E24eB](E24eB.apply)

  implicit val E25fFormat: Format[E25f] = new OptionalIntegerFormat[E25f](E25f.apply)

  implicit val E26Format: Format[E26] = new OptionalIntegerFormat[E26](E26.apply)

  implicit val E27Format: Format[E27] = new OptionalIntegerFormat[E27](E27.apply)

  implicit val E28Format: Format[E28] = new OptionalIntegerFormat[E28](E28.apply)

  implicit val E1000Format: Format[E1000] = new OptionalStringFormat[E1000](E1000.apply)

  implicit val E1001Format: Format[E1001] = new OptionalStringFormat[E1001](E1001.apply)

  implicit val E1010Format: Format[E1010] = new OptionalBooleanFormat[E1010](E1010.apply)

  implicit val E1011Format: Format[E1011] = new OptionalBooleanFormat[E1011](E1011.apply)

  implicit val E1012Format: Format[E1012] = new OptionalBooleanFormat[E1012](E1012.apply)

  implicit val E1013Format: Format[E1013] = new OptionalBooleanFormat[E1013](E1013.apply)

  implicit val E1020Format: Format[E1020] = new StringFormat[E1020](E1020.apply)

  implicit val E1021Format: Format[E1021] = new DateFormat[E1021](E1021.apply)

  implicit val E1022Format: Format[E1022] = new DateFormat[E1022](E1022.apply)

  implicit val E1023Format: Format[E1023] = new StringFormat[E1023](E1023.apply)

  implicit val E1030Format: Format[E1030] = new OptionalStringFormat[E1030](E1030.apply)

  implicit val E1031Format: Format[E1031] = new OptionalStringFormat[E1031](E1031.apply)

  implicit val E1032Format: Format[E1032] = new OptionalDateFormat[E1032](E1032.apply)
}
