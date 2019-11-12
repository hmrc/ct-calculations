/*
 * Copyright 2019 HM Revenue & Customs
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
import uk.gov.hmrc.ct.accounts.{AC4, MockFrs105AccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation

class AC7999aSpec
    extends WordSpec
    with Matchers
    with MockitoSugar
    with MockFrs105AccountsRetriever {

  private def fieldRequiredError(boxID: String) =
    CtValidation(Some(boxID), s"error.$boxID.required")

  private val boxId = "AC7999a"
  private val lastDayBeforeMandatoryNotes = LocalDate.parse("2016-12-31")
  private val mandatoryNotesStartDate = LocalDate.parse("2017-01-01")

  "When the end of the accounting period is before 2017, AC7999a" should {
    "pass validation" when {
      "neither buttons are checked" in {
        when(boxRetriever.ac4()) thenReturn AC4(lastDayBeforeMandatoryNotes)
        AC7999a(None).validate(boxRetriever) shouldBe Set()
      }
    }
  }

  "When the end of the accounting period is after 2016-31-12, AC7999a" should {
    "fail validation" when {
      "when neither radio buttons are checked" in {
        when(boxRetriever.ac4()) thenReturn AC4(mandatoryNotesStartDate)

        AC7999a(None).validate(boxRetriever) shouldBe Set(
          fieldRequiredError(boxId))
      }
    }
    "pass validation" when {
      "either 'yes' or 'no' button is pressed" in {
        when(boxRetriever.ac4()) thenReturn AC4(mandatoryNotesStartDate)
        AC7999a(Some(true)).validate(boxRetriever) shouldBe Set()
      }
    }
  }
}
