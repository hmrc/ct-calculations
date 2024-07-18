/*
 * Copyright 2024 HM Revenue & Customs
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

import play.api.libs.json.{Reads, _}
import uk.gov.hmrc.ct.accounts.frs105.boxes._
import uk.gov.hmrc.ct.box.formats._

package object formats {

  private def withDefault[A](key:String, default:A)(implicit writes:Writes[A]) = __.json.update((__ \ key).json.copyFrom((__ \ key).json.pick orElse Reads.pure(Json.toJson(default))))

  implicit val ac34Frs105Format: OptionalIntegerFormat[AC34] = new OptionalIntegerFormat[AC34](AC34.apply)
  implicit val ac35Frs105Format: OptionalIntegerFormat[AC35] = new OptionalIntegerFormat[AC35](AC35.apply)
  implicit val ac59Frs105Format: OptionalIntegerFormat[AC59] = new OptionalIntegerFormat[AC59](AC59.apply)
  implicit val ac58Frs105Format: OptionalIntegerFormat[AC58] = new OptionalIntegerFormat[AC58](AC58.apply)
  implicit val ac60Frs105Format: OptionalIntegerFormat[AC60] = new OptionalIntegerFormat[AC60](AC60.apply)
  implicit val ac61Frs105Format: OptionalIntegerFormat[AC61] = new OptionalIntegerFormat[AC61](AC61.apply)
  implicit val ac62Frs105Format: OptionalIntegerFormat[AC62] = new OptionalIntegerFormat[AC62](AC62.apply)
  implicit val ac63Frs105Format: OptionalIntegerFormat[AC63] = new OptionalIntegerFormat[AC63](AC63.apply)
  implicit val ac64Frs105Format: OptionalIntegerFormat[AC64] = new OptionalIntegerFormat[AC64](AC64.apply)
  implicit val ac65Frs105Format: OptionalIntegerFormat[AC65] = new OptionalIntegerFormat[AC65](AC65.apply)
  implicit val ac66Frs105Format: OptionalIntegerFormat[AC66] = new OptionalIntegerFormat[AC66](AC66.apply)
  implicit val ac67Frs105Format: OptionalIntegerFormat[AC67] = new OptionalIntegerFormat[AC67](AC67.apply)
  implicit val ac405Frs105Format: OptionalIntegerFormat[AC405] = new OptionalIntegerFormat[AC405](AC405.apply)
  implicit val ac406Frs105Format: OptionalIntegerFormat[AC406] = new OptionalIntegerFormat[AC406](AC406.apply)
  implicit val ac410Frs105Format: OptionalIntegerFormat[AC410] = new OptionalIntegerFormat[AC410](AC410.apply)
  implicit val ac411Frs105Format: OptionalIntegerFormat[AC411] = new OptionalIntegerFormat[AC411](AC411.apply)
  implicit val ac415Frs105Format: OptionalIntegerFormat[AC415] = new OptionalIntegerFormat[AC415](AC415.apply)
  implicit val ac416Frs105Format: OptionalIntegerFormat[AC416] = new OptionalIntegerFormat[AC416](AC416.apply)
  implicit val ac420Frs105Format: OptionalIntegerFormat[AC420] = new OptionalIntegerFormat[AC420](AC420.apply)
  implicit val ac421Frs105Format: OptionalIntegerFormat[AC421] = new OptionalIntegerFormat[AC421](AC421.apply)
  implicit val ac425Frs105Format: OptionalIntegerFormat[AC425] = new OptionalIntegerFormat[AC425](AC425.apply)
  implicit val ac426Frs105Format: OptionalIntegerFormat[AC426] = new OptionalIntegerFormat[AC426](AC426.apply)
  implicit val ac435Frs105Format: OptionalIntegerFormat[AC435] = new OptionalIntegerFormat[AC435](AC435.apply)
  implicit val ac436Frs105Format: OptionalIntegerFormat[AC436] = new OptionalIntegerFormat[AC436](AC436.apply)
  implicit val ac450Frs105Format: OptionalIntegerFormat[AC450] = new OptionalIntegerFormat[AC450](AC450.apply)
  implicit val ac451Frs105Format: OptionalIntegerFormat[AC451] = new OptionalIntegerFormat[AC451](AC451.apply)
  implicit val ac455Frs105Format: OptionalIntegerFormat[AC455] = new OptionalIntegerFormat[AC455](AC455.apply)
  implicit val ac456Frs105Format: OptionalIntegerFormat[AC456] = new OptionalIntegerFormat[AC456](AC456.apply)
  implicit val ac460Frs105Format: OptionalIntegerFormat[AC460] = new OptionalIntegerFormat[AC460](AC460.apply)
  implicit val ac461Frs105Format: OptionalIntegerFormat[AC461] = new OptionalIntegerFormat[AC461](AC461.apply)
  implicit val ac465Frs105Format: OptionalIntegerFormat[AC465] = new OptionalIntegerFormat[AC465](AC465.apply)
  implicit val ac466Frs105Format: OptionalIntegerFormat[AC466] = new OptionalIntegerFormat[AC466](AC466.apply)
  implicit val ac470Frs105Format: OptionalIntegerFormat[AC470] = new OptionalIntegerFormat[AC470](AC470.apply)
  implicit val ac471Frs105Format: OptionalIntegerFormat[AC471] = new OptionalIntegerFormat[AC471](AC471.apply)
  implicit val ac490Frs105Format: OptionalIntegerFormat[AC490] = new OptionalIntegerFormat[AC490](AC490.apply)
  implicit val ac491Frs105Format: OptionalIntegerFormat[AC491] = new OptionalIntegerFormat[AC491](AC491.apply)
  implicit val ac7991Frs105Format: OptionalBooleanFormat[AC7991] = new OptionalBooleanFormat[AC7991](AC7991.apply)
  implicit val ac7992Frs105Format: OptionalBooleanFormat[AC7992] = new OptionalBooleanFormat[AC7992](AC7992.apply)
  implicit val ac7995Frs105Format: OptionalStringFormat[AC7995] = new OptionalStringFormat[AC7995](AC7995.apply)
  implicit val ac7997Frs105Format: OptionalStringFormat[AC7997] = new OptionalStringFormat[AC7997](AC7997.apply)
  implicit val ac7998Frs105Format: OptionalIntegerFormat[AC7998] = new OptionalIntegerFormat[AC7998](AC7998.apply)
  implicit val ac7999Frs105Format: OptionalStringFormat[AC7999] = new OptionalStringFormat[AC7999](AC7999.apply)
  implicit val ac7999aFrs105Format: OptionalBooleanFormat[AC7999a] = new OptionalBooleanFormat[AC7999a](AC7999a.apply)
  implicit val ac8087Format: OptionalBooleanFormat[AC8087] = new OptionalBooleanFormat(AC8087.apply)
}
