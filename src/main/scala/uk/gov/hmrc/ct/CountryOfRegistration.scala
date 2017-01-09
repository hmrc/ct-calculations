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

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box._
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

case class CountryOfRegistration(value: Option[String]) extends CtBoxIdentifier("Country of registration") with CtOptionalString with Input with ValidatableBox[FilingAttributesBoxValueRetriever] {

  override def validate(boxRetriever: FilingAttributesBoxValueRetriever): Set[CtValidation] = {
    import CountryOfRegistration._
    value match {
      case Some(code) if fromCode(code).isEmpty => Set(CtValidation(boxId= Some("CountryOfRegistration"), errorMessageKey = "error.CountryOfRegistration.unknown"))
      case _ => Set.empty
    }
  }
}

object CountryOfRegistration {

  val EnglandWales = CountryOfRegistration(Some("EW"))
  val Scotland = CountryOfRegistration(Some("SC"))
  val NorthernIreland = CountryOfRegistration(Some("NI"))

  val knownCountries = Set(EnglandWales, Scotland, NorthernIreland)

  def fromCode(code: String): Option[CountryOfRegistration] = knownCountries.find(_.value.contains(code))
}
