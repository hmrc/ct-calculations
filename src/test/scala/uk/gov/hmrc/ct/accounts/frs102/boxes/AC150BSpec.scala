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

import org.mockito.Mockito.when
import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, AccountsRequiredValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever

class AC150BSpec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever]
  with AccountsRequiredValidationFixture[Frs102AccountsBoxRetriever]
  with MockFrs102AccountsRetriever {
  when(boxRetriever.ac151B()).thenReturn(AC151B(None))
  testAccountsMoneyValidationWithMin("AC150B", 0, AC150B.apply)
  testAccountsRequiredValidation("AC150B", AC150B.apply, boxRetriever.ac151B, AC151B.apply, boxRetriever)
}
