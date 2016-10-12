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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.frs102.{AccountsMoneyValidationFixture, MockAbridgedAccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation

class AC118Spec extends AccountsMoneyValidationFixture with MockAbridgedAccountsRetriever {

  override def setUpMocks() = {
    super.setUpMocks()

    import boxRetriever._

    when(ac42()).thenReturn(AC42(Some(100)))
    when(ac43()).thenReturn(AC43(Some(100)))
  }

  testAccountsMoneyValidationWithMin("AC118", 0, AC118.apply)

  "AC118" should {
    "not exist when AC43 is empty" in {
      when(boxRetriever.ac43()).thenReturn(AC43(None))
      AC118(Some(5121)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("AC118"), s"error.AC118.cannot.exist", None))
    }
    "be OK when AC43 is provided" in {
      when(boxRetriever.ac43()).thenReturn(AC43(Some(43)))
      AC118(Some(5121)).validate(boxRetriever) shouldBe empty
    }
    "be OK when AC43 is empty and AC118 is empty" in {
      when(boxRetriever.ac43()).thenReturn(AC43(None))
      AC118(None).validate(boxRetriever) shouldBe empty
    }
  }


}
