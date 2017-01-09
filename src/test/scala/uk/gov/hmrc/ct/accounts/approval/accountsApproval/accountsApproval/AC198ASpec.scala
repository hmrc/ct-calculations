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

package uk.gov.hmrc.ct.accounts.approval.accountsApproval.accountsApproval

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.cato.time.DateHelper
import uk.gov.hmrc.ct.accounts.approval.boxes.AC198A
import uk.gov.hmrc.ct.accounts.{AC4, AccountsDatesValidationFixture, MockFrs102AccountsRetriever}
import uk.gov.hmrc.ct.accounts.retriever.AccountsBoxRetriever

class AC198ASpec extends WordSpec
  with MockitoSugar
  with Matchers
  with MockFrs102AccountsRetriever
  with BeforeAndAfter
  with AccountsDatesValidationFixture[AccountsBoxRetriever] {

  val NOW = DateHelper.now()
  val APEnd = NOW.minusMonths(1)

  before{
    when(boxRetriever.ac4()).thenReturn(AC4(APEnd))
  }

  testDateIsMandatory("AC198A", AC198A)

  testDateBetweenIntervalValidation("AC198A", startDate = APEnd, endDate = NOW, AC198A)

}
