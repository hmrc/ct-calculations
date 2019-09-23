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

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.MockFrs105AccountsRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC7999bSpec extends WordSpec with Matchers with MockitoSugar with MockFrs105AccountsRetriever {

  private def fieldRequiredError(boxID: String) = CtValidation(Some(boxID), s"error.$boxID.required")

  private val boxID = "AC7999b"

  when(boxRetriever.ac7999a()).thenReturn(AC7999a(None))

  "AC7999b" should {
    "fail validation if this radio button and AC7999a are unchecked" in {
      AC7999b(Some(false)).validate(boxRetriever) shouldBe Set(fieldRequiredError(boxID))
      AC7999b(None).validate(boxRetriever) shouldBe Set(fieldRequiredError(boxID))
    }

    "pass validation if this radio button is checked and AC7999b is unchecked" in {
      AC7999b(Some(true)).validate(boxRetriever) shouldBe Set.empty
    }
  }
}
