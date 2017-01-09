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

package uk.gov.hmrc.ct.ct600j.v3

import play.api.libs.json.Format
import uk.gov.hmrc.ct.box.formats._

package object formats {

  implicit val J5Format: Format[J5] = new OptionalStringFormat[J5](J5.apply)
  implicit val J10Format: Format[J10] = new OptionalStringFormat[J10](J10.apply)
  implicit val J15Format: Format[J15] = new OptionalStringFormat[J15](J15.apply)
  implicit val J20Format: Format[J20] = new OptionalStringFormat[J20](J20.apply)
  implicit val J25Format: Format[J25] = new OptionalStringFormat[J25](J25.apply)
  implicit val J30Format: Format[J30] = new OptionalStringFormat[J30](J30.apply)
  implicit val J35Format: Format[J35] = new OptionalStringFormat[J35](J35.apply)
  implicit val J40Format: Format[J40] = new OptionalStringFormat[J40](J40.apply)
  implicit val J45Format: Format[J45] = new OptionalStringFormat[J45](J45.apply)
  implicit val J50Format: Format[J50] = new OptionalStringFormat[J50](J50.apply)


  implicit val J5AFormat: Format[J5A] = new OptionalDateFormat[J5A](J5A.apply)
  implicit val J10AFormat: Format[J10A] = new OptionalDateFormat[J10A](J10A.apply)
  implicit val J15AFormat: Format[J15A] = new OptionalDateFormat[J15A](J15A.apply)
  implicit val J20AFormat: Format[J20A] = new OptionalDateFormat[J20A](J20A.apply)
  implicit val J25AFormat: Format[J25A] = new OptionalDateFormat[J25A](J25A.apply)
  implicit val J30AFormat: Format[J30A] = new OptionalDateFormat[J30A](J30A.apply)
  implicit val J35AFormat: Format[J35A] = new OptionalDateFormat[J35A](J35A.apply)
  implicit val J40AFormat: Format[J40A] = new OptionalDateFormat[J40A](J40A.apply)
  implicit val J45AFormat: Format[J45A] = new OptionalDateFormat[J45A](J45A.apply)
  implicit val J50AFormat: Format[J50A] = new OptionalDateFormat[J50A](J50A.apply)
}
