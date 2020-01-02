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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AC3, AccountsIntegerValidationFixture, MockFrs105AccountsRetriever}

class AC7998Spec extends WordSpec with Matchers with MockitoSugar with AccountsIntegerValidationFixture[Frs105AccountsBoxRetriever] with MockFrs105AccountsRetriever {

  private val boxID = "AC7998"
  private val minNumberOfEmployees = Some(0)
  private val maxNumberOfEmployees = Some(99999)
  private val isMandatory = Some(true)
  private val lastDayBeforeMandatoryNotes = LocalDate.parse("2016-12-31")
  private val mandatoryNotesStartDate = LocalDate.parse("2017-01-01")

  "When the beginning of the accounting period is before 2017, AC7998" should {
    "pass validation" when {
      "employee information field is empty" in {
        when(boxRetriever.ac3()) thenReturn AC3(lastDayBeforeMandatoryNotes)
        AC7998(None).validate(boxRetriever) shouldBe Set()
      }
    }
  }

  "When the beginning of the accounting period is after 2016-31-12, AC7998" should {
    "validate correctly" when {
      when(boxRetriever.ac3()) thenReturn AC3(mandatoryNotesStartDate)
      testIntegerFieldValidation(boxID, AC7998, minNumberOfEmployees, maxNumberOfEmployees, isMandatory, true)
    }
  }
}


