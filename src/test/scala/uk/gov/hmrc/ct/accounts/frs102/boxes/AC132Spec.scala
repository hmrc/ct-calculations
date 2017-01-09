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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.{MockFrs102AccountsRetriever, AccountsMoneyValidationFixture}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC132Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  "AC132" should {
    "fail validation when AC132 does not match AC44" in {
      when(boxRetriever.ac44()).thenReturn(AC44(Some(22)))
      when(boxRetriever.ac45()).thenReturn(AC45(Some(22)))
      AC132(Some(11)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.tangible.assets.note.currentNetBookValue.notEqualToAssets"))
    }

    "fail validation when AC132 is set, AC44 is empty and AC45 is set" in {
      when(boxRetriever.ac44()).thenReturn(AC44(None))
      when(boxRetriever.ac45()).thenReturn(AC45(Some(22)))
      AC132(Some(22)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.tangible.assets.note.currentNetBookValue.notEqualToAssets"))
    }

    "pass validation when totals tally" in {
      when(boxRetriever.ac44()).thenReturn(AC44(Some(22)))
      when(boxRetriever.ac45()).thenReturn(AC45(Some(22)))
      AC132(Some(22)).validate(boxRetriever) shouldBe Set()
    }



    "pass validation when no values for note fields or balance sheet value" in {
      when(boxRetriever.ac44()).thenReturn(AC44(None))
      when(boxRetriever.ac45()).thenReturn(AC45(Some(22)))
      AC132(None).validate(boxRetriever) shouldBe Set()
    }
  }
}
