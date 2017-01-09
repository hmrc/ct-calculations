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

class AC115FullSpec extends AccountsMoneyValidationFixture[Frs102AccountsBoxRetriever] with MockFullAccountsRetriever {

  def setupEmptyMocks() = {
    when(boxRetriever.ac42()).thenReturn(AC42(Some(100)))
    when(boxRetriever.ac43()).thenReturn(AC43(Some(100)))

    when(boxRetriever.ac114()).thenReturn(AC114(None))
    when(boxRetriever.ac114A()).thenReturn(AC114A(None))
    when(boxRetriever.ac114B()).thenReturn(AC114B(None))
    when(boxRetriever.ac115()).thenReturn(AC115(None))
    when(boxRetriever.ac115A()).thenReturn(AC115A(None))
    when(boxRetriever.ac115B()).thenReturn(AC115B(None))
    when(boxRetriever.ac116()).thenReturn(AC116(None))
    when(boxRetriever.ac116A()).thenReturn(AC116A(None))
    when(boxRetriever.ac116B()).thenReturn(AC116B(None))
    when(boxRetriever.ac117()).thenReturn(AC117(None))
    when(boxRetriever.ac117A()).thenReturn(AC117A(None))
    when(boxRetriever.ac117B()).thenReturn(AC117B(None))
    when(boxRetriever.ac118()).thenReturn(AC118(None))
    when(boxRetriever.ac118A()).thenReturn(AC118A(None))
    when(boxRetriever.ac118B()).thenReturn(AC118B(None))
    when(boxRetriever.ac119()).thenReturn(AC119(None))
    when(boxRetriever.ac119A()).thenReturn(AC119A(None))
    when(boxRetriever.ac119B()).thenReturn(AC119B(None))
    when(boxRetriever.ac120()).thenReturn(AC120(None))
    when(boxRetriever.ac120A()).thenReturn(AC120A(None))
    when(boxRetriever.ac120B()).thenReturn(AC120B(None))
    when(boxRetriever.ac121()).thenReturn(AC121(None))
    when(boxRetriever.ac121A()).thenReturn(AC121A(None))
    when(boxRetriever.ac121B()).thenReturn(AC121B(None))
    when(boxRetriever.ac122()).thenReturn(AC122(None))
    when(boxRetriever.ac122A()).thenReturn(AC122A(None))
    when(boxRetriever.ac122B()).thenReturn(AC122B(None))
    when(boxRetriever.ac123()).thenReturn(AC123(None))
    when(boxRetriever.ac123A()).thenReturn(AC123A(None))
    when(boxRetriever.ac123B()).thenReturn(AC123B(None))
    when(boxRetriever.ac209()).thenReturn(AC209(None))
    when(boxRetriever.ac209A()).thenReturn(AC209A(None))
    when(boxRetriever.ac209B()).thenReturn(AC209B(None))
    when(boxRetriever.ac210()).thenReturn(AC210(None))
    when(boxRetriever.ac210A()).thenReturn(AC210A(None))
    when(boxRetriever.ac210B()).thenReturn(AC210B(None))
    when(boxRetriever.ac211()).thenReturn(AC211(None))
    when(boxRetriever.ac211A()).thenReturn(AC211A(None))
    when(boxRetriever.ac211B()).thenReturn(AC211B(None))
    when(boxRetriever.ac5123()).thenReturn(AC5123(None))
  }

  override def setUpMocks() = {
    super.setUpMocks()

    setupEmptyMocks()

    when(boxRetriever.ac5123()).thenReturn(AC5123(Some("Test text")))
  }

  testAccountsMoneyValidationWithMin("AC115", 0, AC115.apply, testEmpty = false)

  "AC115" should {

    "throw global error when none of the fields for the note is entered" in {
      setupEmptyMocks()

      AC115(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.intangibleAssets.atLeastOneEntered"))
    }

    "throw global error when one field was entered but not cannot be set" in {
      setupEmptyMocks()

      when(boxRetriever.ac42()).thenReturn(AC42(None))
      when(boxRetriever.ac43()).thenReturn(AC43(None))
      when(boxRetriever.ac5123()).thenReturn(AC5123(Some("Test text")))

      AC115(None).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.balanceSheet.intangibleAssetsNote.cannot.exist"))
    }

    "validate successfully if AC42 is empty, AC43 not and there's a note box set." in {
      setupEmptyMocks()

      when(boxRetriever.ac42()).thenReturn(AC42(None))
      when(boxRetriever.ac43()).thenReturn(AC43(Some(10)))
      when(boxRetriever.ac114()).thenReturn(AC114(Some(10)))
      when(boxRetriever.ac5123()).thenReturn(AC5123(Some("Test text")))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC114 is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac114()).thenReturn(AC114(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC114A is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac114A()).thenReturn(AC114A(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC114B is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac114B()).thenReturn(AC114B(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC115 is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac115()).thenReturn(AC115(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC115A is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac115A()).thenReturn(AC115A(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC115B is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac115B()).thenReturn(AC115B(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC116 is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac116()).thenReturn(AC116(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC116A is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac116A()).thenReturn(AC116A(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC116B is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac116B()).thenReturn(AC116B(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC117 is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac117()).thenReturn(AC117(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC117A is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac117A()).thenReturn(AC117A(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC117B is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac117B()).thenReturn(AC117B(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC118 is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac118()).thenReturn(AC118(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC118A is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac118A()).thenReturn(AC118A(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC118B is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac118B()).thenReturn(AC118B(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC119 is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac119()).thenReturn(AC119(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC119A is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac119A()).thenReturn(AC119A(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC119B is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac119B()).thenReturn(AC119B(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC120 is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac120()).thenReturn(AC120(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC120A is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac120A()).thenReturn(AC120A(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC120B is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac120B()).thenReturn(AC120B(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC121 is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac121()).thenReturn(AC121(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC121A is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac121A()).thenReturn(AC121A(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC121B is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac121B()).thenReturn(AC121B(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC122 is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac122()).thenReturn(AC122(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC122A is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac122A()).thenReturn(AC122A(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC122B is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac122B()).thenReturn(AC122B(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC123 is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac123()).thenReturn(AC123(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC123A is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac123A()).thenReturn(AC123A(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC123B is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac123B()).thenReturn(AC123B(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC209 is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac209()).thenReturn(AC209(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC209A is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac209A()).thenReturn(AC209A(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC209B is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac209B()).thenReturn(AC209B(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC210 is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac210()).thenReturn(AC210(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC210A is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac210A()).thenReturn(AC210A(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC210B is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac210B()).thenReturn(AC210B(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC211 is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac211()).thenReturn(AC211(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC211A is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac211A()).thenReturn(AC211A(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

    "validate successfully when AC211B is set" in {
      setupEmptyMocks()

      when(boxRetriever.ac211B()).thenReturn(AC211B(Some(1)))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }


    "validate successfully if note is empty and AC42 and AC43 are empty" in {
      setupEmptyMocks()

      when(boxRetriever.ac42()).thenReturn(AC42(None))
      when(boxRetriever.ac43()).thenReturn(AC43(None))

      AC115(None).validate(boxRetriever) shouldBe Set.empty
    }

  }

}
