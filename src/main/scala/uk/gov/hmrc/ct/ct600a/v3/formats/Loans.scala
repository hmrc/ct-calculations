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

package uk.gov.hmrc.ct.ct600a.v3.formats

import play.api.libs.json.Json
import uk.gov.hmrc.ct.ct600a.v3.{formats, A25, A10}

object Loans {

  import uk.gov.hmrc.ct.ct600a.v3.formats._

  def A10FromJsonString(json: String): A10 = Json.fromJson[A10](Json.parse(json)).get

  def toJsonString(a10: A10): String =  Json.toJson(a10).toString() //LP02 in v2

  def asBoxString(a10: A10): Option[String] = Some(toJsonString(a10)) // LP02 in v2

  def A25FromJsonString(json: String): A25 = Json.fromJson[A25](Json.parse(json)).get

  def toJsonString(a25: A25): String =  Json.toJson(a25).toString() // LP03 in v2

  def asBoxString(a25: A25): Option[String] = Some(toJsonString(a25))
}
