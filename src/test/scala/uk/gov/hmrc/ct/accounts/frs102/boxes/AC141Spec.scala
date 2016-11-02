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

package uk.gov.hmrc.ct.accounts.frs102.boxes

import org.mockito.Mockito._
import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, MockFullAccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.FullAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC141Spec extends AccountsMoneyValidationFixture[FullAccountsBoxRetriever] with MockFullAccountsRetriever {

  "AC141" should {
    "fail validation if not equal to AC53" in {
      when(boxRetriever.ac52()).thenReturn(AC52(Some(5)))
      when(boxRetriever.ac53()).thenReturn(AC53(Some(13)))
      AC141(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.debtors.previousYearTotal.notEqualsTo.previousYearAmount"))
    }

    "fail validation if empty and AC53 is 0" in {
      when(boxRetriever.ac52()).thenReturn(AC52(Some(5)))
      when(boxRetriever.ac53()).thenReturn(AC53(Some(0)))
      AC141(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.debtors.previousYearTotal.notEqualsTo.previousYearAmount"))
    }

    "pass validation if equal to AC53" in {
      when(boxRetriever.ac52()).thenReturn(AC52(Some(5)))
      when(boxRetriever.ac53()).thenReturn(AC53(Some(13)))
      AC141(Some(13)).validate(boxRetriever) shouldBe Set.empty
    }
  }

}
