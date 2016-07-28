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

package uk.gov.hmrc.ct.accounts.frs10x.abridged

import uk.gov.hmrc.ct.box.formats.{OptionalIntegerFormat, OptionalStringFormat}

package object formats {
  implicit val ac16Format = new OptionalIntegerFormat[AC16](AC16.apply)
  implicit val ac17Format = new OptionalIntegerFormat[AC17](AC17.apply)
  implicit val ac18Format = new OptionalIntegerFormat[AC18](AC18.apply)
  implicit val ac19Format = new OptionalIntegerFormat[AC19](AC19.apply)
  implicit val ac20Format = new OptionalIntegerFormat[AC20](AC20.apply)
  implicit val ac21Format = new OptionalIntegerFormat[AC21](AC21.apply)
  implicit val ac28Format = new OptionalIntegerFormat[AC28](AC28.apply)
  implicit val ac29Format = new OptionalIntegerFormat[AC29](AC29.apply)
  implicit val ac30Format = new OptionalIntegerFormat[AC30](AC30.apply)
  implicit val ac31Format = new OptionalIntegerFormat[AC31](AC31.apply)
  implicit val ac34Format = new OptionalIntegerFormat[AC34](AC34.apply)
  implicit val ac35Format = new OptionalIntegerFormat[AC35](AC35.apply)
  implicit val ac5032Format = new OptionalStringFormat[AC5032](AC5032.apply)
  implicit val ac52Format = new OptionalIntegerFormat[AC52](AC52.apply)
  implicit val ac5052AFormat = new OptionalIntegerFormat[AC5052A](AC5052A.apply)
  implicit val ac5052BFormat = new OptionalStringFormat[AC5052B](AC5052B.apply)
}
