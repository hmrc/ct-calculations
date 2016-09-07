/*
 * Copyright 2016 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs10x.formats

import play.api.libs.json.Json
import uk.gov.hmrc.ct.accounts.frs10x.Directors

object DirectorsFormatter {
  
  def DirectorsFromJsonString(json: String): Directors = Json.fromJson[Directors](Json.parse(json)).get

  def toJsonString(directors: Directors): String =  Json.toJson(directors).toString()

  def asBoxString(directors: Directors): Option[String] = Some(toJsonString(directors))
}
