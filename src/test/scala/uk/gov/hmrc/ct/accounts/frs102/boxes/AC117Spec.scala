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

class AC117Spec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockAbridgedAccountsRetriever {

  override def setUpMocks() = {
    super.setUpMocks()

    import boxRetriever._

    when(ac42()).thenReturn(AC42(Some(100)))
  }

  testAccountsMoneyValidationWithMin("AC117", 0, AC117.apply)

  "AC117" should {

    "validate successfully if nothing is wrong" in {
      setUpMocks()
      AC117(Some(10)).validate(boxRetriever) shouldBe Set.empty
    }

    "correctly perform the calculation" in {
      import boxRetriever._

      when(ac114()).thenReturn(AC114(Some(1)))
      when(ac115()).thenReturn(AC115(Some(1)))
      when(ac116()).thenReturn(AC116(Some(1)))
      when(ac209()).thenReturn(AC209(Some(1)))
      when(ac210()).thenReturn(AC210(Some(1)))

      AC117.calculate(boxRetriever) shouldBe AC117(Some(3))
    }
  }

}
