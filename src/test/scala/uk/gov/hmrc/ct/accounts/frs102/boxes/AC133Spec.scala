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

class AC133Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  "AC133" should {
    "fail validation when AC133 does not match AC45" in {
      when(boxRetriever.ac45()).thenReturn(AC45(Some(22)))
      when(boxRetriever.ac44()).thenReturn(AC44(Some(22)))
      AC133(Some(11)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.tangible.assets.note.previousNetBookValue.notEqualToAssets"))
    }

    "pass validation when totals tally" in {
      when(boxRetriever.ac45()).thenReturn(AC45(Some(11)))
      when(boxRetriever.ac44()).thenReturn(AC44(Some(11)))
      AC133(Some(11)).validate(boxRetriever) shouldBe Set()
    }

    "pass validation when no values for note fields or balance sheet value" in {
      when(boxRetriever.ac45()).thenReturn(AC45(None))
      when(boxRetriever.ac44()).thenReturn(AC44(Some(22)))
      AC133(None).validate(boxRetriever) shouldBe Set()
    }

    "fail validation when no current value balance sheet value but previous year value is set" in {
      when(boxRetriever.ac45()).thenReturn(AC45(Some(11)))
      when(boxRetriever.ac44()).thenReturn(AC44(None))
      AC133(Some(22)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.tangible.assets.note.previousNetBookValue.notEqualToAssets"))
    }

    "not fail validation when no current value balance sheet value" in {
      when(boxRetriever.ac45()).thenReturn(AC45(None))
      when(boxRetriever.ac44()).thenReturn(AC44(None))
      AC133(Some(22)).validate(boxRetriever) shouldBe Set()
    }
  }
}
