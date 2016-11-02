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

package uk.gov.hmrc.ct.accounts.frs102.abridged.loansToDirectors

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.MockFrs102AccountsRetriever
import uk.gov.hmrc.ct.accounts.frs102.AccountsFreeTextSizeRangeValidationFixture
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC304ASpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsFreeTextSizeRangeValidationFixture {

  testMandatoryAccountsCharacterSizeRangeValidation("AC304A", 0, StandardCohoNameFieldLimit, AC304A)

  "AC304A should" should {

    "fail validation when not set" in {
      AC304A(None).validate(null) shouldBe Set(CtValidation(Some("AC304A"), "error.AC304A.required", None))
    }
    "fail validation when not legal name" in {
      AC304A(Some("bad n&me£")).validate(null) shouldBe Set(CtValidation(Some("AC304A"), "error.AC304A.regexFailure"))
    }
  }
}
