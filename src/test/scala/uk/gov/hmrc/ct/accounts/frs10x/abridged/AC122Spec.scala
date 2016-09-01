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

class AC122Spec extends AccountsMoneyValidationFixture with MockAbridgedAccountsRetriever {

  override def setUpMocks() = {
    super.setUpMocks()

    import boxRetriever._

    when(ac42()).thenReturn(AC42(Some(100)))
  }

  "AC122" should {

    "throw error when is set when AC42 is empty" in {
      setUpMocks()
      when(boxRetriever.ac42()).thenReturn(AC42(None))
      AC122(Some(100)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC122"), "error.AC122.cannot.exist"))
    }

    "throw error when is different than AC43" in {
      setUpMocks()
      AC122(Some(10)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC122"), "error.AC122.mustEqual.AC42"))
    }

    "validate successfully if nothing is wrong" in {
      setUpMocks()
      AC122(Some(100)).validate(boxRetriever) shouldBe Set.empty
    }

    "correctly perform the calculation" in {
      import boxRetriever._

      when(ac117()).thenReturn(AC117(Some(1)))
      when(ac121()).thenReturn(AC121(Some(1)))

      AC122.calculate(boxRetriever) shouldBe AC122(Some(0))
    }

  }

}
