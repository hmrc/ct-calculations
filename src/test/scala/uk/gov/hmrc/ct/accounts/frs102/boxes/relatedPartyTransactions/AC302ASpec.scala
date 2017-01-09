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

package uk.gov.hmrc.ct.accounts.frs102.boxes.relatedPartyTransactions

import org.joda.time.LocalDate
import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.{MockFrs102AccountsRetriever, AccountsMoneyValidationFixture, AC206}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever

class AC302ASpec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockFrs102AccountsRetriever {

  override def setUpMocks() = {
    super.setUpMocks()
    when(boxRetriever.ac206()).thenReturn(AC206(Some(new LocalDate())))
  }

  testAccountsMoneyValidationWithMin("AC302A", 0, AC302A.apply)

  "AC301A" should {
    "always pass if no previous POA" in {
      when(boxRetriever.ac206()).thenReturn(AC206(None))
      AC302A(Some(-99)).validate(boxRetriever) shouldBe Set()
    }
  }
}
