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
import uk.gov.hmrc.ct.accounts.{MockAbridgedAccountsRetriever, AccountsMoneyValidationFixture}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC123Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockAbridgedAccountsRetriever {

  override def setUpMocks() = {
    super.setUpMocks()

    import boxRetriever._

    when(ac42()).thenReturn(AC42(Some(100)))
    when(ac43()).thenReturn(AC43(Some(100)))
  }

  "AC123" should {

    "throw error when is different than AC43" in {
      setUpMocks()
      AC123(Some(10)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.intangible.assets.note.previousNetBookValue.notEqualToAssets"))
    }

    "pass validation if has value but AC42 and AC43 are empty" in {
      import boxRetriever._

      when(ac42()).thenReturn(AC42(None))
      when(ac43()).thenReturn(AC43(None))

      AC123(Some(10)).validate(boxRetriever) shouldBe Set.empty
    }

    "fail validation if this and AC42 have values but AC43 is empty" in {
      import boxRetriever._

      when(ac42()).thenReturn(AC42(Some(10)))
      when(ac43()).thenReturn(AC43(None))

      AC123(Some(10)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.intangible.assets.note.previousNetBookValue.notEqualToAssets"))
    }

    "validate successfully if nothing is wrong" in {
      setUpMocks()
      AC123(Some(100)).validate(boxRetriever) shouldBe Set.empty
    }

    "correctly perform the calculation when both numbers are set" in {
      import boxRetriever._

      when(ac114()).thenReturn(AC114(Some(1)))
      when(ac118()).thenReturn(AC118(Some(1)))

      AC123.calculate(boxRetriever) shouldBe AC123(Some(0))
    }

    "correctly perform the calculation when only one number is set" in {
      import boxRetriever._

      when(ac114()).thenReturn(AC114(None))
      when(ac118()).thenReturn(AC118(Some(1)))

      AC123.calculate(boxRetriever) shouldBe AC123(Some(-1))
    }

    "correctly perform the calculation when no numbers are set" in {
      import boxRetriever._

      when(ac114()).thenReturn(AC114(None))
      when(ac118()).thenReturn(AC118(None))

      AC123.calculate(boxRetriever) shouldBe AC123(None)
    }

  }

}
