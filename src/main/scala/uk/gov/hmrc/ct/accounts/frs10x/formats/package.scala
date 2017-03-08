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

  implicit val ac8021Format: Format[AC8021] = new OptionalBooleanFormat[AC8021](AC8021.apply)
  implicit val ac8023Format: Format[AC8023] = new OptionalBooleanFormat[AC8023](AC8023.apply)
  implicit val ac8051Format: Format[AC8051] = new OptionalStringFormat[AC8051](AC8051.apply)
  implicit val ac8052Format: Format[AC8052] = new OptionalStringFormat[AC8052](AC8052.apply)
  implicit val ac8053Format: Format[AC8053] = new OptionalStringFormat[AC8053](AC8053.apply)
  implicit val ac8054Format: Format[AC8054] = new OptionalStringFormat[AC8054](AC8054.apply)
  implicit val ac8899Format: Format[AC8899] = new OptionalBooleanFormat[AC8899](AC8899.apply)

  implicit val directorFormat = Json.format[Director]
  implicit val directorsFormat: Format[Directors] = Json.format[Directors]
  implicit val ac8033Format: Format[AC8033] = new OptionalStringFormat[AC8033](AC8033.apply)
  implicit val acq8003Format: Format[ACQ8003] = new OptionalBooleanFormat[ACQ8003](ACQ8003.apply)
  implicit val acq8009Format: Format[ACQ8009] = new OptionalBooleanFormat[ACQ8009](ACQ8009.apply)

  implicit val acq8991Format: Format[ACQ8991] = new OptionalBooleanFormat[ACQ8991](ACQ8991.apply)
  implicit val acq8999Format: Format[ACQ8999] = new OptionalBooleanFormat[ACQ8999](ACQ8999.apply)
}
