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
import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.MockMandatoryNotesRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.CP2

class AC7999aSpec extends WordSpec with Matchers with MockitoSugar with MockMandatoryNotesRetriever {

  private def fieldRequiredError(boxID: String) = CtValidation(Some(boxID),s"error.$boxID.required")
  private val boxId = "AC7999a"
  private val mandatoryNotesStartDate = LocalDate.parse("2017-01-01")
  private val mandatoryNotesNotEnabledDate = LocalDate.parse("2016-12-31")

  "AC7999a" should {
    "fail validation" when {
      "neither radio buttons are checked and the accounting period is after or on the 2017-01-01" in {
        when(boxRetriever.cp2()) thenReturn CP2(mandatoryNotesStartDate)
      AC7999a(None).validate(boxRetriever) shouldBe Set(fieldRequiredError(boxId))
    }
      }
    "pass validation" when {
      "'no' button is checked" in {
        when(boxRetriever.cp2()) thenReturn CP2(mandatoryNotesStartDate)
        AC7999a(Some(true)).validate(boxRetriever) shouldBe Set.empty
      }

      "neither boxes are checked and the accounting period is before 2017-01-01" in  {
        when(boxRetriever.cp2()) thenReturn CP2(mandatoryNotesNotEnabledDate)
        AC7999a(None).validate(boxRetriever) shouldBe Set.empty
      }
    }
    }


}

