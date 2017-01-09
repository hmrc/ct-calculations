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
import uk.gov.hmrc.ct.accounts.{MockFullAccountsRetriever, AccountsMoneyValidationFixture}
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC162Spec extends AccountsMoneyValidationFixture[FullAccountsBoxRetriever]
                with MockFullAccountsRetriever {

  "AC162" should {
    "fail validation if value is not equal to AC64" in {
      when(boxRetriever.ac64()).thenReturn(AC64(Some(10)))
      when(boxRetriever.ac65()).thenReturn(AC65(Some(10)))
      AC162(Some(5)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.creditorsAfterOneYear.currentYearTotal.notEqualsTo.currentYearAmount"))
    }

    "fail validation if value is present and AC64 is empty" in {
      when(boxRetriever.ac64()).thenReturn(AC64(None))
      when(boxRetriever.ac65()).thenReturn(AC65(Some(10)))
      AC162(Some(100)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.creditorsAfterOneYear.currentYearTotal.notEqualsTo.currentYearAmount"))
    }

    "fail validation if value is empty and AC64 is zero" in {
      when(boxRetriever.ac64()).thenReturn(AC64(Some(0)))
      when(boxRetriever.ac65()).thenReturn(AC65(Some(10)))
      AC162(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.creditorsAfterOneYear.currentYearTotal.notEqualsTo.currentYearAmount"))
    }

    "pass validation if value is equal to AC64" in {
      when(boxRetriever.ac64()).thenReturn(AC64(Some(10)))
      when(boxRetriever.ac65()).thenReturn(AC65(Some(10)))
      AC162(Some(10)).validate(boxRetriever) shouldBe Set.empty
    }
  }

}
