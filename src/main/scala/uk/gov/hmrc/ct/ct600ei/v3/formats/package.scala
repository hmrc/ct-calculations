/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.ct.ct600ei.v3

import play.api.libs.json.Format
import uk.gov.hmrc.ct.box.formats.OptionalBooleanFormat
import uk.gov.hmrc.ct.ct600.v3.{B616, B617, B618}

package object formats {
  implicit val DIT001Format: Format[DIT001] = new OptionalBooleanFormat[DIT001](DIT001.apply)
  implicit val DIT002Format: Format[DIT002] = new OptionalBooleanFormat[DIT002](DIT002.apply)
  implicit val DIT003Format: Format[DIT003] = new OptionalBooleanFormat[DIT003](DIT003.apply)
  implicit val B616Format: Format[B616] = new OptionalBooleanFormat[B616](B616.apply)
  implicit val B617Format: Format[B617] = new OptionalBooleanFormat[B617](B617.apply)
  implicit val B618Format: Format[B618] = new OptionalBooleanFormat[B618](B618.apply)

}
