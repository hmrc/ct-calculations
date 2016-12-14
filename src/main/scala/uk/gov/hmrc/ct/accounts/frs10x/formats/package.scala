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

package uk.gov.hmrc.ct.accounts.frs10x

import play.api.libs.json.{Reads, _}
import uk.gov.hmrc.ct.accounts.approval.boxes._
import uk.gov.hmrc.ct.accounts.frs10x.boxes._
import uk.gov.hmrc.ct.box.formats._

package object formats {

  implicit val ac8081Format = new OptionalBooleanFormat(AC8081.apply)
  implicit val ac8082Format = new OptionalBooleanFormat(AC8082.apply)
  implicit val ac8083Format = new OptionalBooleanFormat(AC8083.apply)
  implicit val ac8088Format = new OptionalBooleanFormat(AC8088.apply)

  implicit val acq8161Format = new OptionalBooleanFormat[ACQ8161](ACQ8161.apply)

}
