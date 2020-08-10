/*
 * Copyright 2020 HM Revenue & Customs
 *
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
