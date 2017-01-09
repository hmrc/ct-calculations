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

package uk.gov.hmrc.ct.accounts

import play.api.libs.json.{Format, Json}
import uk.gov.hmrc.ct.box.formats.{DateFormat, OptionalDateFormat, OptionalIntegerFormat, OptionalStringFormat}

package object formats {

  implicit val companyAddressFormat: Format[CompanyAddress] = Json.format[CompanyAddress]

  implicit val ac1Format: Format[AC1] = new OptionalStringFormat[AC1](AC1.apply)
  implicit val ac2Format: Format[AC2] = new OptionalStringFormat[AC2](AC2.apply)
  implicit val ac3Format: Format[AC3] = new DateFormat[AC3](AC3.apply)
  implicit val ac4Format: Format[AC4] = new DateFormat[AC4](AC4.apply)
  implicit val ac12Format: Format[AC12] = new OptionalIntegerFormat[AC12](AC12.apply)

  implicit val ac205Format: Format[AC205] = new OptionalDateFormat[AC205](AC205.apply)
  implicit val ac206Format: Format[AC206] = new OptionalDateFormat[AC206](AC206.apply)
}
