/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP43Spec extends WordSpec with MockitoSugar with Matchers with BoxValidationFixture[ComputationsBoxRetriever] {

  val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]
  private val outOfRangeError = Set(CtValidation(Some("CP43"), "error.CP43.outOfRange", Some(Seq("0", "99,999,999"))))
  private val validationSuccess = Set()

  "fail validation" when {
      "the inputted number is greater than 8 characters" in {
        CP43(Some(123456789)).validate(boxRetriever) shouldBe outOfRangeError
      }

      "the inputted number is negative" in {
        CP43(Some(-1)).validate(boxRetriever) shouldBe outOfRangeError
      }
    }

  "pass validation" when {
    "nothing is inputted" in {
      CP43(None).validate(boxRetriever) shouldBe validationSuccess
    }
    "number between 0 and 999999999 is inputted" in {
      CP43(Some(100)).validate(boxRetriever) shouldBe validationSuccess
    }
  }
}
