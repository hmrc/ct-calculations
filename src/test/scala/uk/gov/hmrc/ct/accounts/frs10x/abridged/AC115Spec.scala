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

class AC115Spec extends AccountsMoneyValidationFixture with MockAbridgedAccountsRetriever {

  override def setUpMocks() = {
    super.setUpMocks()

    import boxRetriever._

    when(ac42()).thenReturn(AC42(Some(100)))
    when(ac43()).thenReturn(AC43(Some(100)))
    when(ac5117()).thenReturn(AC5117(None))
    when(ac115()).thenReturn(AC115(None))
    when(ac116()).thenReturn(AC116(None))
    when(ac209()).thenReturn(AC209(None))
    when(ac210()).thenReturn(AC210(None))
    when(ac5121()).thenReturn(AC5121(None))
    when(ac119()).thenReturn(AC119(None))
    when(ac120()).thenReturn(AC120(None))
    when(ac211()).thenReturn(AC211(None))
    when(ac5123()).thenReturn(AC5123(Some("test text")))
  }

  testAccountsMoneyValidationWithMin("AC115", 0, AC115.apply, testEmpty = false)

  "AC115" should {

    "throw error when is set when AC42 is empty" in {
      setUpMocks()
      when(boxRetriever.ac42()).thenReturn(AC42(None))
      AC115(Some(10)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC115"), "error.AC115.cannot.exist"))
    }

    "throw global error when none of the fields for the note is entered" in {
      setUpMocks()
      when(boxRetriever.ac5123()).thenReturn(AC5123(None))
      AC115(Some(10)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.intangibleAssets.atLeastOneEntered"))
    }

    "validate successfully if nothing is wrong" in {
      setUpMocks()
      AC115(Some(10)).validate(boxRetriever) shouldBe Set.empty
    }

  }

}
