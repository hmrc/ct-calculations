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

import org.scalatest.mock.MockitoSugar
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
