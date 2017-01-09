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

package uk.gov.hmrc.ct.ct600a.v3.formats

import play.api.libs.json.Json
import uk.gov.hmrc.ct.ct600a.v3.LoansToParticipators

object LoansFormatter {

  import uk.gov.hmrc.ct.ct600a.v3.formats._

  def LoansFromJsonString(json: String): LoansToParticipators = Json.fromJson[LoansToParticipators](Json.parse(json)).get

  def toJsonString(loans2p: LoansToParticipators): String =  Json.toJson(loans2p).toString()

  def asBoxString(loans2p: LoansToParticipators): Option[String] = Some(toJsonString(loans2p))

}
