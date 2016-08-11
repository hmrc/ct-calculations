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
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.{AccountsMoneyValidationFixture, MockAbridgedAccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC5052ASpec extends WordSpec with MockitoSugar with Matchers with MockAbridgedAccountsRetriever with AccountsMoneyValidationFixture with BeforeAndAfter {

  before {
    when(boxRetriever.ac52).thenReturn(AC52(Some(33)))
  }

  testAccountsMoneyValidationWithMin("AC5052A", minValue = 0, AC5052A)

  "returns AC52 required validation error when AC5052A populated and AC52 is empty" in {
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    AC5052A(Some(4)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC52"), "error.AC52.required"))
   }
}
