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

package uk.gov.hmrc.ct.ct600e.validations

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v3.E10
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

class ValidateRegisteredCharityNumberSpec extends WordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[CT600EBoxRetriever]

  "ValidateRegisteredCharityNumber" should {

    "return no errors if value is between 6-8 characters and all digits" in new ValidateRegisteredCharityNumber {
      validate(Some("123456"), "E10").isEmpty shouldBe true
    }

    "return no errors if value is None" in new ValidateRegisteredCharityNumber {
      validate(None, "E10").isEmpty shouldBe true
    }

    "return an error if value is less then 6 characters" in new ValidateRegisteredCharityNumber {
      validate(Some("12345"), "E10") shouldBe Set(CtValidation(Some("E10"), "error.E10.invalidRegNumber"))
    }

    "return an error if value is more then 8 characters" in new ValidateRegisteredCharityNumber {
      validate(Some("123456789"), "E10") shouldBe Set(CtValidation(Some("E10"), "error.E10.invalidRegNumber"))
    }

    "return an error if value has correct number of characters but contains any non-digit" in new ValidateRegisteredCharityNumber {
      validate(Some("12345a78"), "E10") shouldBe Set(CtValidation(Some("E10"), "error.E10.invalidRegNumber"))
    }
  }

}
