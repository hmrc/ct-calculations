/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever

class CountryOfRegistrationSpec extends WordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[FilingAttributesBoxValueRetriever]

  "CountryOfRegistration" should {
    "pass validation if empty" in {
      CountryOfRegistration(None).validate(boxRetriever) shouldBe empty
    }
    "pass validation for EW" in {
      CountryOfRegistration(Some("EW")).validate(boxRetriever) shouldBe empty
    }
    "pass validation for SC" in {
      CountryOfRegistration(Some("SC")).validate(boxRetriever) shouldBe empty
    }
    "pass validation for IR" in {
      CountryOfRegistration(Some("NI")).validate(boxRetriever) shouldBe empty
    }
    "fail validation for empty string" in {
      CountryOfRegistration(Some("")).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("CountryOfRegistration"), errorMessageKey = "error.CountryOfRegistration.unknown"))
    }
    "fail validation for unknown code" in {
      CountryOfRegistration(Some("AU")).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("CountryOfRegistration"), errorMessageKey = "error.CountryOfRegistration.unknown"))
    }
  }
}
