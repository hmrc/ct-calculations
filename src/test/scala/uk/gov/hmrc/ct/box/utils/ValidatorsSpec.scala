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

package uk.gov.hmrc.ct.box.utils

import uk.gov.hmrc.ct.box.{CtValidation, Validators}
import uk.gov.hmrc.ct.utils.UnitSpec

class ValidatorsSpec extends UnitSpec with Validators {

  "errorMessage" should {

    val required = "required"
    val errorArg1 = 1
    val errorArg2 = 2
    val errorMessageKey = s"error.$boxId.$required"

    "return an error message without an argument" in {
      errorMessage(required) shouldBe fieldRequiredError(boxId)
    }

    "return an error message with one argument" in {
      errorMessage(required, Seq(errorArg1)) shouldBe
        Set(CtValidation(Some(boxId), errorMessageKey, Some(Seq(errorArg1.toString))))
    }

    "return an error message with multiple arguments" in {
      errorMessage(required, Seq(errorArg1, errorArg2)) shouldBe
        Set(CtValidation(Some(boxId), errorMessageKey, Some(Seq(errorArg1.toString, errorArg2.toString))))
    }
  }
}
