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

package uk.gov.hmrc.ct.accounts.frs10x.abridged

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.frs10x.{AccountsMoneyValidationFixture, MockAbridgedAccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation

class AC5131Spec extends AccountsMoneyValidationFixture with MockAbridgedAccountsRetriever {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac45()).thenReturn(AC45(Some(45)))
    super.setUpMocks()
  }

  testAccountsMoneyValidationWithMin("AC5131",0, AC5131.apply)

  "AC5131" should {
    "not exist when AC45 is empty" in {
      when(boxRetriever.ac45()).thenReturn(AC45(None))
      AC5131(Some(5217)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("AC5131"), s"error.AC5131.cannot.exist", None))
    }
    "be OK when AC45 is provided" in {
      when(boxRetriever.ac45()).thenReturn(AC45(Some(45)))
      AC5131(Some(5217)).validate(boxRetriever) shouldBe empty
    }
    "be OK when AC45 is empty and AC5131 is empty" in {
      when(boxRetriever.ac45()).thenReturn(AC45(None))
      AC5131(None).validate(boxRetriever) shouldBe empty
    }
  }
}
