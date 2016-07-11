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

package uk.gov.hmrc.ct.accounts.frs10x

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation

class DirectorsDetailsSpec extends WordSpec with Matchers {

  "DirectorsDetails" should {

    "validate AC8001 successfully when no validation errors are present" in {

      val directorDetails = DirectorDetails("444", "luke")
      val directorsDetails = DirectorsDetails(List(directorDetails))

      val expectedError = Set()
      directorDetails.validate(null) shouldBe expectedError
      directorsDetails.validate(null) shouldBe expectedError
    }

    "validate AC8001 director name length" in {

      val directorDetails = DirectorDetails("444", "")
      val directorsDetails = DirectorsDetails(List(directorDetails))

      val expectedError = Set(CtValidation(Some("AC8001"), "error.AC8001.text.sizeRange", Some(List("1", "40"))))
      directorDetails.validate(null) shouldBe expectedError
      directorsDetails.validate(null) shouldBe expectedError
    }

    "validate AC8001 director name characters" in {

      val directorDetails = DirectorDetails("444", "??")
      val directorsDetails = DirectorsDetails(List(directorDetails))

      val expectedError = Set(CtValidation(Some("AC8001"), "error.AC8001.regexFailure", None))
      directorDetails.validate(null) shouldBe expectedError
      directorsDetails.validate(null) shouldBe expectedError
    }


  }
}
