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
import uk.gov.hmrc.ct.box.CtValidation

class AC125AbridgedSpec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockAbridgedAccountsRetriever {

  override def setUpMocks() = {
    when(boxRetriever.ac44()).thenReturn(AC44(Some(99)))

    clearMockTangibleAssetsFields()
    when(boxRetriever.ac125()).thenReturn(AC125(Some(99)))
    super.setUpMocks()
  }

  private def clearMockTangibleAssetsFields() = {
    when(boxRetriever.ac124()).thenReturn(AC124(None))
    when(boxRetriever.ac125()).thenReturn(AC125(None))
    when(boxRetriever.ac126()).thenReturn(AC126(None))
    when(boxRetriever.ac212()).thenReturn(AC212(None))
    when(boxRetriever.ac213()).thenReturn(AC213(None))
    when(boxRetriever.ac128()).thenReturn(AC128(None))
    when(boxRetriever.ac219()).thenReturn(AC219(None))
    when(boxRetriever.ac130()).thenReturn(AC130(None))
    when(boxRetriever.ac214()).thenReturn(AC214(None))
    when(boxRetriever.ac5133()).thenReturn(AC5133(None))
  }

  testAccountsMoneyValidationWithMin("AC125", 0, AC125.apply)

  "AC125" should {
    "fail validation when at least one intangible assets field is not populated" in {
      when(boxRetriever.ac125()).thenReturn(AC125(None))
      AC125(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.tangible.assets.note.one.box.required"))
    }

    "fail validation when note cannot be populated" in {
      when(boxRetriever.ac44()).thenReturn(AC44(None))
      when(boxRetriever.ac45()).thenReturn(AC45(None))
      when(boxRetriever.ac128()).thenReturn(AC128(Some(123)))
      AC125(Some(10)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.tangibleAssetsNote.cannot.exist"))
    }

    "pass validation if no fields populated and AC44 not populated" in {
      clearMockTangibleAssetsFields()
      when(boxRetriever.ac44()).thenReturn(AC44(None))
      AC125(None).validate(boxRetriever) shouldBe Set()
    }

    "pass validation if one field populated" in  {
      when(boxRetriever.ac44()).thenReturn(AC44(Some(1)))
      when(boxRetriever.ac125()).thenReturn(AC125(None))
      when(boxRetriever.ac124()).thenReturn(AC124(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac125()).thenReturn(AC125(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac126()).thenReturn(AC126(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac212()).thenReturn(AC212(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac213()).thenReturn(AC213(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac128()).thenReturn(AC128(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac219()).thenReturn(AC219(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac130()).thenReturn(AC130(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac214()).thenReturn(AC214(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac5133()).thenReturn(AC5133(Some("hello")))
      AC125(None).validate(boxRetriever) shouldBe Set()
    }
  }
}
