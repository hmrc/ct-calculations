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

package uk.gov.hmrc.ct.accounts.frs102.abridged

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.frs102.{AccountsMoneyValidationFixture, MockAbridgedAccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation

class AC128Spec extends AccountsMoneyValidationFixture with MockAbridgedAccountsRetriever {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac45()).thenReturn(AC45(Some(45)))
    super.setUpMocks()
  }

  testAccountsMoneyValidationWithMin("AC128",0, AC128.apply)

  "AC128" should {
    "not exist when AC45 is empty" in {
      when(boxRetriever.ac45()).thenReturn(AC45(None))
      AC128(Some(5217)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("AC128"), s"error.AC128.cannot.exist", None))
    }
    "be OK when AC45 is provided" in {
      when(boxRetriever.ac45()).thenReturn(AC45(Some(45)))
      AC128(Some(5217)).validate(boxRetriever) shouldBe empty
    }
    "be OK when AC45 is empty and AC128 is empty" in {
      when(boxRetriever.ac45()).thenReturn(AC45(None))
      AC128(None).validate(boxRetriever) shouldBe empty
    }
  }
}
