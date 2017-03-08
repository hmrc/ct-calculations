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
import uk.gov.hmrc.ct.accounts.frsse2008.micro._
import uk.gov.hmrc.ct.box.formats.{OptionalDateFormat, OptionalIntegerFormat}

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
}
