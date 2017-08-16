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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.mockito.Mockito.when
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, AccountsRequiredValidationFixture, MockFrs105AccountsRetriever}

class AC64Spec extends AccountsMoneyValidationFixture[Frs105AccountsBoxRetriever]
  with AccountsRequiredValidationFixture[Frs105AccountsBoxRetriever]
  with MockFrs105AccountsRetriever {
  when(boxRetriever.ac65()).thenReturn(AC65(None))
  testAccountsMoneyValidationWithMin("AC64", minValue = 0, AC64)
  testAccountsRequiredValidation("AC64", AC64.apply, boxRetriever.ac65, AC65.apply, boxRetriever)
}
