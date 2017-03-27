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

package uk.gov.hmrc.ct.accounts.frsse2008

import play.api.libs.json.Format
import uk.gov.hmrc.ct.accounts.AC12
import uk.gov.hmrc.ct.accounts.frsse2008.boxes._
import uk.gov.hmrc.ct.accounts.frsse2008.boxes.micro._
import uk.gov.hmrc.ct.box.formats.{OptionalBooleanFormat, OptionalDateFormat, OptionalIntegerFormat}

package object formats {

  implicit val ac13Format: Format[AC13] = new OptionalIntegerFormat[AC13](AC13.apply)
  implicit val ac14Format: Format[AC14] = new OptionalIntegerFormat[AC14](AC14.apply)
  implicit val ac15Format: Format[AC15] = new OptionalIntegerFormat[AC15](AC15.apply)
  implicit val ac16Format: Format[AC16] = new OptionalIntegerFormat[AC16](AC16.apply)
  implicit val ac17Format: Format[AC17] = new OptionalIntegerFormat[AC17](AC17.apply)
  implicit val ac18Format: Format[AC18] = new OptionalIntegerFormat[AC18](AC18.apply)
  implicit val ac19Format: Format[AC19] = new OptionalIntegerFormat[AC19](AC19.apply)
  implicit val ac20Format: Format[AC20] = new OptionalIntegerFormat[AC20](AC20.apply)
  implicit val ac21Format: Format[AC21] = new OptionalIntegerFormat[AC21](AC21.apply)
  implicit val ac22Format: Format[AC22] = new OptionalIntegerFormat[AC22](AC22.apply)
  implicit val ac23Format: Format[AC23] = new OptionalIntegerFormat[AC23](AC23.apply)
  implicit val ac26Format: Format[AC26] = new OptionalIntegerFormat[AC26](AC26.apply)
  implicit val ac27Format: Format[AC27] = new OptionalIntegerFormat[AC27](AC27.apply)
  implicit val ac28Format: Format[AC28] = new OptionalIntegerFormat[AC28](AC28.apply)
  implicit val ac29Format: Format[AC29] = new OptionalIntegerFormat[AC29](AC29.apply)
  implicit val ac30Format: Format[AC30] = new OptionalIntegerFormat[AC30](AC30.apply)
  implicit val ac31Format: Format[AC31] = new OptionalIntegerFormat[AC31](AC31.apply)
  implicit val ac32Format: Format[AC32] = new OptionalIntegerFormat[AC32](AC32.apply)
  implicit val ac33Format: Format[AC33] = new OptionalIntegerFormat[AC33](AC33.apply)
  implicit val ac34Format: Format[AC34] = new OptionalIntegerFormat[AC34](AC34.apply)
  implicit val ac35Format: Format[AC35] = new OptionalIntegerFormat[AC35](AC35.apply)
  implicit val ac36Format: Format[AC36] = new OptionalIntegerFormat[AC36](AC36.apply)
  implicit val ac37Format: Format[AC37] = new OptionalIntegerFormat[AC37](AC37.apply)
  implicit val ac38Format: Format[AC38] = new OptionalIntegerFormat[AC38](AC38.apply)
  implicit val ac39Format: Format[AC39] = new OptionalIntegerFormat[AC39](AC39.apply)
  implicit val ac40Format: Format[AC40] = new OptionalIntegerFormat[AC40](AC40.apply)
  implicit val ac41Format: Format[AC41] = new OptionalIntegerFormat[AC41](AC41.apply)

  implicit val ac405Format: Format[AC405] = new OptionalIntegerFormat[AC405](AC405.apply)
  implicit val ac406Format: Format[AC406] = new OptionalIntegerFormat[AC406](AC406.apply)
  implicit val ac410Format: Format[AC410] = new OptionalIntegerFormat[AC410](AC410.apply)
  implicit val ac411Format: Format[AC411] = new OptionalIntegerFormat[AC411](AC411.apply)
  implicit val ac415Format: Format[AC415] = new OptionalIntegerFormat[AC415](AC415.apply)
  implicit val ac416Format: Format[AC416] = new OptionalIntegerFormat[AC416](AC416.apply)
  implicit val ac420Format: Format[AC420] = new OptionalIntegerFormat[AC420](AC420.apply)
  implicit val ac421Format: Format[AC421] = new OptionalIntegerFormat[AC421](AC421.apply)
  implicit val ac425Format: Format[AC425] = new OptionalIntegerFormat[AC425](AC425.apply)
  implicit val ac426Format: Format[AC426] = new OptionalIntegerFormat[AC426](AC426.apply)
  implicit val ac435Format: Format[AC435] = new OptionalIntegerFormat[AC435](AC435.apply)
  implicit val ac436Format: Format[AC436] = new OptionalIntegerFormat[AC436](AC436.apply)

  implicit val ac492Format: Format[AC492] = new OptionalBooleanFormat[AC492](AC492.apply)
  implicit val ac493Format: Format[AC493] = new OptionalBooleanFormat[AC493](AC493.apply)
  implicit val ac494Format: Format[AC494] = new OptionalBooleanFormat[AC494](AC494.apply)

  implicit val acq8001Format: Format[ACQ8001] = new OptionalBooleanFormat[ACQ8001](ACQ8001.apply)
  implicit val acq8003Format: Format[ACQ8003] = new OptionalBooleanFormat[ACQ8003](ACQ8003.apply)
  implicit val acq8004Format: Format[ACQ8004] = new OptionalBooleanFormat[ACQ8004](ACQ8004.apply)

  implicit val acq8101Format: Format[ACQ8101] = new OptionalBooleanFormat[ACQ8101](ACQ8101.apply)
  implicit val acq8102Format: Format[ACQ8102] = new OptionalBooleanFormat[ACQ8102](ACQ8102.apply)
  implicit val acq8103Format: Format[ACQ8103] = new OptionalBooleanFormat[ACQ8103](ACQ8103.apply)
  implicit val acq8104Format: Format[ACQ8104] = new OptionalBooleanFormat[ACQ8104](ACQ8104.apply)
  implicit val acq8105Format: Format[ACQ8105] = new OptionalBooleanFormat[ACQ8105](ACQ8105.apply)
  implicit val acq8106Format: Format[ACQ8106] = new OptionalBooleanFormat[ACQ8106](ACQ8106.apply)
  implicit val acq8107Format: Format[ACQ8107] = new OptionalBooleanFormat[ACQ8107](ACQ8107.apply)
  implicit val acq8108Format: Format[ACQ8108] = new OptionalBooleanFormat[ACQ8108](ACQ8108.apply)
  implicit val acq8109Format: Format[ACQ8109] = new OptionalBooleanFormat[ACQ8109](ACQ8109.apply)
  implicit val acq8110Format: Format[ACQ8110] = new OptionalBooleanFormat[ACQ8110](ACQ8110.apply)
  implicit val acq8111Format: Format[ACQ8111] = new OptionalBooleanFormat[ACQ8111](ACQ8111.apply)
  implicit val acq8112Format: Format[ACQ8112] = new OptionalBooleanFormat[ACQ8112](ACQ8112.apply)
  implicit val acq8113Format: Format[ACQ8113] = new OptionalBooleanFormat[ACQ8113](ACQ8113.apply)
  implicit val acq8114Format: Format[ACQ8114] = new OptionalBooleanFormat[ACQ8114](ACQ8114.apply)
  implicit val acq8115Format: Format[ACQ8115] = new OptionalBooleanFormat[ACQ8115](ACQ8115.apply)
  implicit val acq8116Format: Format[ACQ8116] = new OptionalBooleanFormat[ACQ8116](ACQ8116.apply)
  implicit val acq8117Format: Format[ACQ8117] = new OptionalBooleanFormat[ACQ8117](ACQ8117.apply)
  implicit val acq8118Format: Format[ACQ8118] = new OptionalBooleanFormat[ACQ8118](ACQ8118.apply)
  implicit val acq8119Format: Format[ACQ8119] = new OptionalBooleanFormat[ACQ8119](ACQ8119.apply)
  implicit val acq8120Format: Format[ACQ8120] = new OptionalBooleanFormat[ACQ8120](ACQ8120.apply)
  implicit val acq8121Format: Format[ACQ8121] = new OptionalBooleanFormat[ACQ8121](ACQ8121.apply)
  implicit val acq8122Format: Format[ACQ8122] = new OptionalBooleanFormat[ACQ8122](ACQ8122.apply)
  implicit val acq8123Format: Format[ACQ8123] = new OptionalBooleanFormat[ACQ8123](ACQ8123.apply)
  implicit val acq8124Format: Format[ACQ8124] = new OptionalBooleanFormat[ACQ8124](ACQ8124.apply)

  implicit val acq8125Format: Format[ACQ8125] = new OptionalBooleanFormat[ACQ8125](ACQ8125.apply)
  implicit val acq8126Format: Format[ACQ8126] = new OptionalBooleanFormat[ACQ8126](ACQ8126.apply)
  implicit val acq8130Format: Format[ACQ8130] = new OptionalBooleanFormat[ACQ8130](ACQ8130.apply)

  implicit val acq8200Format: Format[ACQ8200] = new OptionalBooleanFormat[ACQ8200](ACQ8200.apply)
  implicit val acq8201Format: Format[ACQ8201] = new OptionalBooleanFormat[ACQ8201](ACQ8201.apply)
  implicit val acq8210Format: Format[ACQ8210] = new OptionalBooleanFormat[ACQ8210](ACQ8210.apply)
  implicit val acq8211Format: Format[ACQ8211] = new OptionalBooleanFormat[ACQ8211](ACQ8211.apply)
  implicit val acq8212Format: Format[ACQ8212] = new OptionalBooleanFormat[ACQ8212](ACQ8212.apply)
  implicit val acq8213Format: Format[ACQ8213] = new OptionalBooleanFormat[ACQ8213](ACQ8213.apply)
  implicit val acq8214Format: Format[ACQ8214] = new OptionalBooleanFormat[ACQ8214](ACQ8214.apply)

  implicit val acq8999Format: Format[ACQ8999] = new OptionalBooleanFormat[ACQ8999](ACQ8999.apply)
}
