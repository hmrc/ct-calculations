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

package uk.gov.hmrc.ct.accounts.frs102.abridged.validation

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs102.validation.CompoundBoxValidationHelper
import uk.gov.hmrc.ct.box.CtValidation

class CompoundBoxValidationHelperSpec extends WordSpec with Matchers {

    "contextualiseErrorKey" should {

      "convert message key to format that includes container index and keyword 'compoundList" in {
        CompoundBoxValidationHelper.contextualiseErrorKey("container", "error.BoxId.some.message", 2) shouldBe
          "error.compoundList.container.2.BoxId.some.message"
      }

      "convert global message key to format that includes compound box name, container index and keywords 'compoundList'" in {
        CompoundBoxValidationHelper.contextualiseError("OwningBox", "container", CtValidation(Some("local"), "error.BoxId.some.message"), 2) shouldBe
          CtValidation(Some("OwningBox"), "error.compoundList.container.2.BoxId.some.message")

        CompoundBoxValidationHelper.contextualiseError("LoansToDirectors", "loans", CtValidation(None, "error.LoansToDirectors.one.field.required"), 2) shouldBe
          CtValidation(None, "error.LoansToDirectors.compoundList.loans.2.one.field.required")
      }
  }
}
