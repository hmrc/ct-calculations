/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import uk.gov.hmrc.ct.accounts.frs10x.boxes.AC15
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, AccountsPreviousPeriodValidationFixture, MockFullAccountsRetriever}

class AC15Spec extends AccountsMoneyValidationFixture[Frs10xAccountsBoxRetriever] with AccountsPreviousPeriodValidationFixture[Frs10xAccountsBoxRetriever] with MockFullAccountsRetriever {

  testAccountsMoneyValidationWithMin("AC15", 0, AC15.apply)

  testAccountsPreviousPoAValidation("AC15", AC15.apply)
}
