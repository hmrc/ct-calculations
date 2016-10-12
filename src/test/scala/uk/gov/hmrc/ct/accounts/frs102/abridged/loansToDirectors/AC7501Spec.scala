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
import uk.gov.hmrc.ct.accounts.frs102.{AccountsFreeTextSizeRangeValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.box.ValidatableBox._

class AC7501Spec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with AccountsFreeTextSizeRangeValidationFixture {

  testMandatoryAccountsCharacterSizeRangeValidation("AC7501", 0, StandardCohoTextFieldLimit , AC7501)
  testAccountsCoHoTextFieldValidation("AC7501", AC7501)
}
