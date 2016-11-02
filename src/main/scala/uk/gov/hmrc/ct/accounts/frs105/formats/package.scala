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

package uk.gov.hmrc.ct.accounts.frs105

import play.api.libs.json.{JsResult, JsValue, Reads, _}
import uk.gov.hmrc.ct.accounts.frs105.boxes._
import uk.gov.hmrc.ct.box.formats._

package object formats {

  private def withDefault[A](key:String, default:A)(implicit writes:Writes[A]) = __.json.update((__ \ key).json.copyFrom((__ \ key).json.pick orElse Reads.pure(Json.toJson(default))))

  implicit val ac58Format = new OptionalIntegerFormat[AC58](AC58.apply)
  implicit val ac59Format = new OptionalIntegerFormat[AC59](AC59.apply)
  implicit val ac60Format = new OptionalIntegerFormat[AC60](AC60.apply)
  implicit val ac61Format = new OptionalIntegerFormat[AC61](AC61.apply)
  implicit val ac62Format = new OptionalIntegerFormat[AC62](AC62.apply)
  implicit val ac63Format = new OptionalIntegerFormat[AC63](AC63.apply)
  implicit val ac64Format = new OptionalIntegerFormat[AC64](AC64.apply)
  implicit val ac65Format = new OptionalIntegerFormat[AC65](AC65.apply)
  implicit val ac66Format = new OptionalIntegerFormat[AC66](AC66.apply)
  implicit val ac67Format = new OptionalIntegerFormat[AC67](AC67.apply)
  implicit val ac450Format = new OptionalIntegerFormat[AC450](AC450.apply)
  implicit val ac451Format = new OptionalIntegerFormat[AC451](AC451.apply)
  implicit val ac455Format = new OptionalIntegerFormat[AC455](AC455.apply)
  implicit val ac456Format = new OptionalIntegerFormat[AC456](AC456.apply)
  implicit val ac460Format = new OptionalIntegerFormat[AC460](AC460.apply)
  implicit val ac461Format = new OptionalIntegerFormat[AC461](AC461.apply)
  implicit val ac465Format = new OptionalIntegerFormat[AC465](AC465.apply)
  implicit val ac466Format = new OptionalIntegerFormat[AC466](AC466.apply)
  implicit val ac470Format = new OptionalIntegerFormat[AC470](AC470.apply)
  implicit val ac471Format = new OptionalIntegerFormat[AC471](AC471.apply)
  implicit val ac490Format = new OptionalIntegerFormat[AC490](AC490.apply)
  implicit val ac491Format = new OptionalIntegerFormat[AC491](AC491.apply)


}
