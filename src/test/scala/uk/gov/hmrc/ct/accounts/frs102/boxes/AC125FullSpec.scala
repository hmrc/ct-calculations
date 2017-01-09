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
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC125FullSpec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockFullAccountsRetriever {

  override def setUpMocks() = {
    when(boxRetriever.ac44()).thenReturn(AC44(Some(99)))

    clearMockTangibleAssetsFields()
    when(boxRetriever.ac125()).thenReturn(AC125(Some(99)))
    super.setUpMocks()
  }

  private def clearMockTangibleAssetsFields() = {
    when(boxRetriever.ac124()).thenReturn(AC124(None))
    when(boxRetriever.ac124A()).thenReturn(AC124A(None))
    when(boxRetriever.ac124B()).thenReturn(AC124B(None))
    when(boxRetriever.ac124C()).thenReturn(AC124C(None))
    when(boxRetriever.ac124D()).thenReturn(AC124D(None))
    when(boxRetriever.ac124E()).thenReturn(AC124E(None))
    when(boxRetriever.ac125A()).thenReturn(AC125A(None))
    when(boxRetriever.ac125B()).thenReturn(AC125B(None))
    when(boxRetriever.ac125C()).thenReturn(AC125C(None))
    when(boxRetriever.ac125D()).thenReturn(AC125D(None))
    when(boxRetriever.ac125E()).thenReturn(AC125E(None))
    when(boxRetriever.ac126()).thenReturn(AC126(None))
    when(boxRetriever.ac126A()).thenReturn(AC126A(None))
    when(boxRetriever.ac126B()).thenReturn(AC126B(None))
    when(boxRetriever.ac126C()).thenReturn(AC126C(None))
    when(boxRetriever.ac126D()).thenReturn(AC126D(None))
    when(boxRetriever.ac126E()).thenReturn(AC126E(None))
    when(boxRetriever.ac127()).thenReturn(AC127(None))
    when(boxRetriever.ac127A()).thenReturn(AC127A(None))
    when(boxRetriever.ac127B()).thenReturn(AC127B(None))
    when(boxRetriever.ac127C()).thenReturn(AC127C(None))
    when(boxRetriever.ac127D()).thenReturn(AC127D(None))
    when(boxRetriever.ac127E()).thenReturn(AC127E(None))
    when(boxRetriever.ac128()).thenReturn(AC128(None))
    when(boxRetriever.ac128A()).thenReturn(AC128A(None))
    when(boxRetriever.ac128B()).thenReturn(AC128B(None))
    when(boxRetriever.ac128C()).thenReturn(AC128C(None))
    when(boxRetriever.ac128D()).thenReturn(AC128D(None))
    when(boxRetriever.ac128E()).thenReturn(AC128E(None))
    when(boxRetriever.ac129()).thenReturn(AC129(None))
    when(boxRetriever.ac129A()).thenReturn(AC129A(None))
    when(boxRetriever.ac129B()).thenReturn(AC129B(None))
    when(boxRetriever.ac129C()).thenReturn(AC129C(None))
    when(boxRetriever.ac129D()).thenReturn(AC129D(None))
    when(boxRetriever.ac129E()).thenReturn(AC129E(None))
    when(boxRetriever.ac130()).thenReturn(AC130(None))
    when(boxRetriever.ac130A()).thenReturn(AC130A(None))
    when(boxRetriever.ac130B()).thenReturn(AC130B(None))
    when(boxRetriever.ac130C()).thenReturn(AC130C(None))
    when(boxRetriever.ac130D()).thenReturn(AC130D(None))
    when(boxRetriever.ac130E()).thenReturn(AC130E(None))
    when(boxRetriever.ac131()).thenReturn(AC131(None))
    when(boxRetriever.ac131A()).thenReturn(AC131A(None))
    when(boxRetriever.ac131B()).thenReturn(AC131B(None))
    when(boxRetriever.ac131C()).thenReturn(AC131C(None))
    when(boxRetriever.ac131D()).thenReturn(AC131D(None))
    when(boxRetriever.ac131E()).thenReturn(AC131E(None))
    when(boxRetriever.ac132()).thenReturn(AC132(None))
    when(boxRetriever.ac132A()).thenReturn(AC132A(None))
    when(boxRetriever.ac132B()).thenReturn(AC132B(None))
    when(boxRetriever.ac132C()).thenReturn(AC132C(None))
    when(boxRetriever.ac132D()).thenReturn(AC132D(None))
    when(boxRetriever.ac132E()).thenReturn(AC132E(None))
    when(boxRetriever.ac133()).thenReturn(AC133(None))
    when(boxRetriever.ac133A()).thenReturn(AC133A(None))
    when(boxRetriever.ac133B()).thenReturn(AC133B(None))
    when(boxRetriever.ac133C()).thenReturn(AC133C(None))
    when(boxRetriever.ac133D()).thenReturn(AC133D(None))
    when(boxRetriever.ac133E()).thenReturn(AC133E(None))
    when(boxRetriever.ac212()).thenReturn(AC212(None))
    when(boxRetriever.ac212A()).thenReturn(AC212A(None))
    when(boxRetriever.ac212B()).thenReturn(AC212B(None))
    when(boxRetriever.ac212C()).thenReturn(AC212C(None))
    when(boxRetriever.ac212D()).thenReturn(AC212D(None))
    when(boxRetriever.ac212E()).thenReturn(AC212E(None))
    when(boxRetriever.ac213()).thenReturn(AC213(None))
    when(boxRetriever.ac213A()).thenReturn(AC213A(None))
    when(boxRetriever.ac213B()).thenReturn(AC213B(None))
    when(boxRetriever.ac213C()).thenReturn(AC213C(None))
    when(boxRetriever.ac213D()).thenReturn(AC213D(None))
    when(boxRetriever.ac213E()).thenReturn(AC213E(None))
    when(boxRetriever.ac214()).thenReturn(AC214(None))
    when(boxRetriever.ac214A()).thenReturn(AC214A(None))
    when(boxRetriever.ac214B()).thenReturn(AC214B(None))
    when(boxRetriever.ac214C()).thenReturn(AC214C(None))
    when(boxRetriever.ac214D()).thenReturn(AC214D(None))
    when(boxRetriever.ac214E()).thenReturn(AC214E(None))
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

    "pass validation if AC45 is set together with PY box" in {
      clearMockTangibleAssetsFields()
      when(boxRetriever.ac44()).thenReturn(AC44(None))
      when(boxRetriever.ac45()).thenReturn(AC45(Some(10)))
      when(boxRetriever.ac124A()).thenReturn(AC124A(Some(10)))
      AC125(None).validate(boxRetriever) shouldBe Set()
    }

    "pass validation if no fields populated and AC44 and AC45 not populated" in {
      clearMockTangibleAssetsFields()
      when(boxRetriever.ac44()).thenReturn(AC44(None))
      when(boxRetriever.ac45()).thenReturn(AC45(None))
      AC125(None).validate(boxRetriever) shouldBe Set()
    }

    "pass validation if one field populated" in  {
      when(boxRetriever.ac44()).thenReturn(AC44(Some(1)))
      when(boxRetriever.ac125()).thenReturn(AC125(None))
      when(boxRetriever.ac124()).thenReturn(AC124(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac124()).thenReturn(AC124(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac124A()).thenReturn(AC124A(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac124B()).thenReturn(AC124B(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac124C()).thenReturn(AC124C(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac124D()).thenReturn(AC124D(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac124E()).thenReturn(AC124E(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac125()).thenReturn(AC125(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac125A()).thenReturn(AC125A(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac125B()).thenReturn(AC125B(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac125C()).thenReturn(AC125C(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac125D()).thenReturn(AC125D(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac125E()).thenReturn(AC125E(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac126()).thenReturn(AC126(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac126A()).thenReturn(AC126A(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac126B()).thenReturn(AC126B(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac126C()).thenReturn(AC126C(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac126D()).thenReturn(AC126D(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac126E()).thenReturn(AC126E(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac127()).thenReturn(AC127(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac127A()).thenReturn(AC127A(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac127B()).thenReturn(AC127B(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac127C()).thenReturn(AC127C(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac127D()).thenReturn(AC127D(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac127E()).thenReturn(AC127E(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac128()).thenReturn(AC128(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac128A()).thenReturn(AC128A(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac128B()).thenReturn(AC128B(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac128C()).thenReturn(AC128C(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac128D()).thenReturn(AC128D(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac128E()).thenReturn(AC128E(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac129()).thenReturn(AC129(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac129A()).thenReturn(AC129A(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac129B()).thenReturn(AC129B(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac129C()).thenReturn(AC129C(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac129D()).thenReturn(AC129D(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac129E()).thenReturn(AC129E(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac130()).thenReturn(AC130(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac130A()).thenReturn(AC130A(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac130B()).thenReturn(AC130B(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac130C()).thenReturn(AC130C(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac130D()).thenReturn(AC130D(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac130E()).thenReturn(AC130E(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac131()).thenReturn(AC131(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac131A()).thenReturn(AC131A(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac131B()).thenReturn(AC131B(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac131C()).thenReturn(AC131C(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac131D()).thenReturn(AC131D(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac131E()).thenReturn(AC131E(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac132()).thenReturn(AC132(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac132A()).thenReturn(AC132A(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac132B()).thenReturn(AC132B(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac132C()).thenReturn(AC132C(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac132D()).thenReturn(AC132D(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac132E()).thenReturn(AC132E(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac133()).thenReturn(AC133(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac133A()).thenReturn(AC133A(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac133B()).thenReturn(AC133B(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac133C()).thenReturn(AC133C(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac133D()).thenReturn(AC133D(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac133E()).thenReturn(AC133E(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac212()).thenReturn(AC212(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac212A()).thenReturn(AC212A(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac212B()).thenReturn(AC212B(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac212C()).thenReturn(AC212C(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac212D()).thenReturn(AC212D(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac212E()).thenReturn(AC212E(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac213()).thenReturn(AC213(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac213A()).thenReturn(AC213A(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac213B()).thenReturn(AC213B(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac213C()).thenReturn(AC213C(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac213D()).thenReturn(AC213D(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac213E()).thenReturn(AC213E(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac214()).thenReturn(AC214(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac214A()).thenReturn(AC214A(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac214B()).thenReturn(AC214B(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac214C()).thenReturn(AC214C(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac214D()).thenReturn(AC214D(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac214E()).thenReturn(AC214E(Some(99)))
      AC125(None).validate(boxRetriever) shouldBe Set()

      clearMockTangibleAssetsFields()
      when(boxRetriever.ac5133()).thenReturn(AC5133(Some("hello")))
      AC125(None).validate(boxRetriever) shouldBe Set()
    }
  }
}
