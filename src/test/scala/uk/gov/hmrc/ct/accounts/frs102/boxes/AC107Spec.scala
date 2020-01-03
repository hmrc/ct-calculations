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

package uk.gov.hmrc.ct.accounts.frs102.boxes


import org.joda.time.LocalDate
import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.{AC205, AccountsIntegerValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.utils.AdditionalNotesAndFootnotesHelper
import uk.gov.hmrc.ct.box.CtValidation

class AC107Spec extends AccountsIntegerValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever with AdditionalNotesAndFootnotesHelper {

  private def validateAC107(inputField: Option[Int], validationResult: Set[CtValidation]) = AC107(inputField).validate(boxRetriever) shouldBe validationResult

  override val boxId: String = "AC107"

  testIntegerFieldValidation(boxId, AC107, Some(minNumberOfEmployees), Some(maxNumberOfEmployees), Some(true), false)

 private val previousPeriodOfAccounts:  AC205 = AC205(Some(LocalDate.now()))
 private val emptyPreviousPeriodOfAccounts:  AC205 = AC205(None)

  "AC107" should {

    "have errors if blank and the user has a previous period of accounts" in {
      when(boxRetriever.ac205()) thenReturn previousPeriodOfAccounts
      validateAC107(None, fieldRequiredError(boxId))
    }

    "not validate with any errors when AC107 has a value and user has a previous period of accounts" in {
      when(boxRetriever.ac205()) thenReturn previousPeriodOfAccounts
      validateAC107(Some(10), validationSuccess)
    }

    "not validate with any errors AC107 has no value and the user does not have a previous period of accounts" in {
      when(boxRetriever.ac205()) thenReturn emptyPreviousPeriodOfAccounts
      validateAC107(None, validationSuccess)
    }

    "validate correctly when the user has a previous accounting period" when {
      when(boxRetriever.ac205()) thenReturn previousPeriodOfAccounts
      testIntegerFieldValidation(boxId, AC107, Some(minNumberOfEmployees), Some(maxNumberOfEmployees), Some(true), false)
    }

    // need to decide whether we're going to keep the exists errors.

//    "validate with should return exist error when AC7300 is None and AC107 has a value" in {
//
//      AC107(Some(100)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC107"), "error.AC107.cannot.exist"))
//    }

//    "validate with should return exist error when AC107 has a value" in {
//
//      AC107(Some(100)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC107"), "error.AC107.cannot.exist"))
//    }
//
//    "validate with should return exist error when AC7300 is true, AC107 has a value and Previous PoA is empty" in {
//      when(boxRetriever.ac205()).thenReturn(AC205(None))
//
//      AC107(Some(100)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC107"), "error.AC107.cannot.exist"))
//    }
  }
}
