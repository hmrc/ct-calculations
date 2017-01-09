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

class AC121Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockAbridgedAccountsRetriever {

  override def setUpMocks() = {
    super.setUpMocks()

    import boxRetriever._

    when(ac42()).thenReturn(AC42(Some(100)))
  }

  "AC121" should {

    "correctly perform the calculation" in {
      import boxRetriever._

      when(ac118()).thenReturn(AC118(Some(1)))
      when(ac119()).thenReturn(AC119(Some(1)))
      when(ac120()).thenReturn(AC120(Some(1)))
      when(ac211()).thenReturn(AC211(Some(1)))

      AC121.calculate(boxRetriever) shouldBe AC121(Some(2))
    }

  }

}
