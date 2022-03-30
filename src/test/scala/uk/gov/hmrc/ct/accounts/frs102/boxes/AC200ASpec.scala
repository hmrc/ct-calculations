/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.MockFrs102AccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC200ASpec extends WordSpec
  with Matchers
  with MockitoSugar
  with MockFrs102AccountsRetriever {

  private val boxId = "AC200A"

  private def fieldRequiredError() =
    CtValidation(Some(boxId), s"error.$boxId.required")

  private val validationSuccess: Set[CtValidation] = Set()

  "AC200A" should {
    "fail validation" when {
      "When neither yes or no is selected" in {
        AC200A(None).validate(boxRetriever) shouldBe Set(fieldRequiredError())
      }
    }
    "pass validation" when {
      "yes or no is selected" in {
        AC200A(Some(false)).validate(boxRetriever) shouldBe validationSuccess
        AC200A(Some(true)).validate(boxRetriever) shouldBe validationSuccess
      }
    }
  }
}
