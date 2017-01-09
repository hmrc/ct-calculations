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
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.{AccountsMoneyValidationFixture, MockAbridgedAccountsRetriever, MockFrs102AccountsRetriever, MockFullAccountsRetriever}
import uk.gov.hmrc.ct.accounts.frs102.retriever.Frs102AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC5052AAbridgedSpec extends WordSpec with MockitoSugar with Matchers with MockAbridgedAccountsRetriever with AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with BeforeAndAfter {

  before {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(STANDARD_MAX + 1)))
    when(boxRetriever.ac53()).thenReturn(AC53(Some(STANDARD_MAX + 1)))
    when(boxRetriever.ac5052A()).thenReturn(AC5052A(Some(10)))
    when(boxRetriever.ac5052B()).thenReturn(AC5052B(Some("asd asd")))
    when(boxRetriever.ac5052C()).thenReturn(AC5052C(Some(10)))
  }

  testAccountsMoneyValidationWithMin("AC5052A", minValue = 0, AC5052A)

  "fail validation when AC5052A is set and AC52 is empty" in {
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac5052A()).thenReturn(AC5052A(Some(10)))
    when(boxRetriever.ac5052B()).thenReturn(AC5052B(None))
    when(boxRetriever.ac5052C()).thenReturn(AC5052C(None))

    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "fail validation when AC5052B is set and AC52 is empty" in {
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac5052A()).thenReturn(AC5052A(None))
    when(boxRetriever.ac5052B()).thenReturn(AC5052B(None))
    when(boxRetriever.ac5052C()).thenReturn(AC5052C(Some(10)))

    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "pass validation when note is empty and AC52 is empty" in {
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac5052A()).thenReturn(AC5052A(None))
    when(boxRetriever.ac5052B()).thenReturn(AC5052B(None))
    when(boxRetriever.ac5052C()).thenReturn(AC5052C(None))

    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "fail validation when AC5052C is set and AC52 is empty" in {
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac5052A()).thenReturn(AC5052A(None))
    when(boxRetriever.ac5052B()).thenReturn(AC5052B(Some("sadf")))
    when(boxRetriever.ac5052C()).thenReturn(AC5052C(None))

    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "fail validation when greater than AC52" in {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(30)))
    AC5052A(Some(31)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5052A"), "error.AC5052A.mustBeLessOrEqual.AC52"))
  }

  "pass validation when equals AC52" in {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(30)))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when less than AC52" in {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(30)))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }
}

class AC5052AFullSpec extends WordSpec with MockitoSugar with Matchers with MockFullAccountsRetriever with AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with BeforeAndAfter {

  before {
    when(boxRetriever.ac52()).thenReturn(AC52(Some(STANDARD_MAX + 1)))
    when(boxRetriever.ac53()).thenReturn(AC53(Some(STANDARD_MAX + 1)))
    when(boxRetriever.ac134()).thenReturn(AC134(Some(10)))
  }

  private def setupEmpty(): Unit = {
    when(boxRetriever.ac134()).thenReturn(AC134(None))
    when(boxRetriever.ac135()).thenReturn(AC135(None))
    when(boxRetriever.ac138()).thenReturn(AC138(None))
    when(boxRetriever.ac139()).thenReturn(AC139(None))
    when(boxRetriever.ac136()).thenReturn(AC136(None))
    when(boxRetriever.ac137()).thenReturn(AC137(None))
    when(boxRetriever.ac140()).thenReturn(AC140(None))
    when(boxRetriever.ac141()).thenReturn(AC141(None))
    when(boxRetriever.ac5052A()).thenReturn(AC5052A(None))
    when(boxRetriever.ac5052B()).thenReturn(AC5052B(None))
    when(boxRetriever.ac5052C()).thenReturn(AC5052C(None))
  }

  private def setupFull(): Unit = {
    when(boxRetriever.ac134()).thenReturn(AC134(Some(10)))
    when(boxRetriever.ac135()).thenReturn(AC135(Some(10)))
    when(boxRetriever.ac138()).thenReturn(AC138(Some(10)))
    when(boxRetriever.ac139()).thenReturn(AC139(Some(10)))
    when(boxRetriever.ac136()).thenReturn(AC136(Some(10)))
    when(boxRetriever.ac137()).thenReturn(AC137(Some(10)))
    when(boxRetriever.ac140()).thenReturn(AC140(Some(10)))
    when(boxRetriever.ac141()).thenReturn(AC141(Some(10)))
    when(boxRetriever.ac5052A()).thenReturn(AC5052A(Some(10)))
    when(boxRetriever.ac5052B()).thenReturn(AC5052B(Some("test")))
    when(boxRetriever.ac5052C()).thenReturn(AC5052C(Some(10)))
  }

  testAccountsMoneyValidationWithMin("AC5052A", minValue = 0, AC5052A)

  "pass validation when note is empty and AC52 is empty" in {
    setupEmpty()
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "fail validation when AC134 is set and AC52 is empty" in {
    setupEmpty()
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac134()).thenReturn(AC134(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "fail validation when AC135 is set and AC52 is empty" in {
    setupEmpty()
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac135()).thenReturn(AC135(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "fail validation when AC138 is set and AC52 is empty" in {
    setupEmpty()
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac138()).thenReturn(AC138(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "fail validation when AC139 is set and AC52 is empty" in {
    setupEmpty()
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac139()).thenReturn(AC139(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "fail validation when AC136 is set and AC52 is empty" in {
    setupEmpty()
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac136()).thenReturn(AC136(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "fail validation when AC137 is set and AC52 is empty" in {
    setupEmpty()
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac137()).thenReturn(AC137(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "fail validation when AC140 is set and AC52 is empty" in {
    setupEmpty()
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac140()).thenReturn(AC140(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "fail validation when AC141 is set and AC52 is empty" in {
    setupEmpty()
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac141()).thenReturn(AC141(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "fail validation when AC5052A is set and AC52 is empty" in {
    setupEmpty()
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac5052A()).thenReturn(AC5052A(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "fail validation when AC5052B is set and AC52 is empty" in {
    setupEmpty()
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac5052B()).thenReturn(AC5052B(Some("test")))
    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "fail validation when AC5052C is set and AC52 is empty" in {
    setupEmpty()
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    when(boxRetriever.ac5052C()).thenReturn(AC5052C(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.cannotExist"))
  }

  "pass validation when note is not set and AC52 and AC53 are empty" in {
    setupEmpty()
    when(boxRetriever.ac52()).thenReturn(AC52(None))
    when(boxRetriever.ac53()).thenReturn(AC53(None))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "fail validation when note is not set and AC52 is not empty" in {
    setupEmpty()

    AC5052A(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.debtors.mustNotBeEmpty"))
  }

  "pass validation when AC134 is set and AC52 is not empty" in {
    setupEmpty()
    when(boxRetriever.ac134()).thenReturn(AC134(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when AC135 is set and AC52 is not empty" in {
    setupEmpty()
    when(boxRetriever.ac135()).thenReturn(AC135(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when AC138 is set and AC52 is not empty" in {
    setupEmpty()
    when(boxRetriever.ac138()).thenReturn(AC138(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when AC139 is set and AC52 is not empty" in {
    setupEmpty()
    when(boxRetriever.ac139()).thenReturn(AC139(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when AC136 is set and AC52 is not empty" in {
    setupEmpty()
    when(boxRetriever.ac136()).thenReturn(AC136(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when AC137 is set and AC52 is not empty" in {
    setupEmpty()
    when(boxRetriever.ac137()).thenReturn(AC137(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when AC140 is set and AC52 is not empty" in {
    setupEmpty()
    when(boxRetriever.ac140()).thenReturn(AC140(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when AC141 is set and AC52 is not empty" in {
    setupEmpty()
    when(boxRetriever.ac141()).thenReturn(AC141(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when AC5052A is set and AC52 is not empty" in {
    setupEmpty()
    when(boxRetriever.ac5052A()).thenReturn(AC5052A(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when AC5052B is set and AC52 is not empty" in {
    setupEmpty()
    when(boxRetriever.ac5052B()).thenReturn(AC5052B(Some("test")))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when AC5052C is set and AC52 is not empty" in {
    setupEmpty()
    when(boxRetriever.ac5052C()).thenReturn(AC5052C(Some(10)))
    AC5052A(None).validate(boxRetriever) shouldBe Set.empty
  }

  "fail validation when greater than AC52" in {
    setupFull()

    when(boxRetriever.ac52()).thenReturn(AC52(Some(30)))
    AC5052A(Some(35)).validate(boxRetriever) shouldBe Set(CtValidation(Some("AC5052A"), "error.AC5052A.mustBeLessOrEqual.AC52"))
  }

  "pass validation when equals AC52" in {
    setupFull()

    when(boxRetriever.ac52()).thenReturn(AC52(Some(30)))
    AC5052A(Some(30)).validate(boxRetriever) shouldBe Set.empty
  }

  "pass validation when less than AC52" in {
    setupFull()

    when(boxRetriever.ac52()).thenReturn(AC52(Some(30)))
    AC5052A(Some(25)).validate(boxRetriever) shouldBe Set.empty
  }
}
