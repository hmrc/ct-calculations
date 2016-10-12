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
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.frs102.{AccountsMoneyValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.box.CtValidation

class AC124Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  override def setUpMocks(): Unit = {
    when(boxRetriever.ac45()).thenReturn(AC45(Some(45)))
    super.setUpMocks()
  }

  testAccountsMoneyValidationWithMin("AC124",0, AC124.apply)

  "AC124" should {
    "not exist when AC45 is empty" in {
      when(boxRetriever.ac45()).thenReturn(AC45(None))
      AC124(Some(5217)).validate(boxRetriever) shouldBe Set(CtValidation(boxId = Some("AC124"), s"error.AC124.cannot.exist", None))
    }
    "be OK when AC45 is provided" in {
      when(boxRetriever.ac45()).thenReturn(AC45(Some(45)))
      AC124(Some(5217)).validate(boxRetriever) shouldBe empty
    }
    "be OK when AC45 is empty and AC124 is empty" in {
      when(boxRetriever.ac45()).thenReturn(AC45(None))
      AC124(None).validate(boxRetriever) shouldBe empty
    }
  }
}
