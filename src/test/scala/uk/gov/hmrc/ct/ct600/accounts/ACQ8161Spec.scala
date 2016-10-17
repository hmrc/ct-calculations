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

package uk.gov.hmrc.ct.ct600.accounts

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.{CompaniesHouseFiling, HMRCFiling}
import uk.gov.hmrc.ct.accounts.frs10x.ACQ8161
import uk.gov.hmrc.ct.accounts.frs10x.abridged._
import uk.gov.hmrc.ct.accounts.frs10x.abridged.retriever.AbridgedAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever


class ACQ8161Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfterEach {

  private trait TestBoxRetriever extends AbridgedAccountsBoxRetriever with FilingAttributesBoxValueRetriever

  private var mockBoxRetriever: TestBoxRetriever = _

  override protected def beforeEach(): Unit = {
    super.beforeEach()

    mockBoxRetriever = mock[TestBoxRetriever]
    when(mockBoxRetriever.ac16()).thenReturn(AC16(Some(10)))
    when(mockBoxRetriever.ac17()).thenReturn(AC17(Some(10)))
    when(mockBoxRetriever.ac18()).thenReturn(AC18(Some(10)))
    when(mockBoxRetriever.ac19()).thenReturn(AC19(Some(10)))
    when(mockBoxRetriever.ac20()).thenReturn(AC20(Some(10)))
    when(mockBoxRetriever.ac21()).thenReturn(AC21(Some(10)))
    when(mockBoxRetriever.ac26()).thenReturn(AC26(Some(10)))
    when(mockBoxRetriever.ac27()).thenReturn(AC27(Some(10)))
    when(mockBoxRetriever.ac28()).thenReturn(AC28(Some(10)))
    when(mockBoxRetriever.ac29()).thenReturn(AC29(Some(10)))
    when(mockBoxRetriever.ac30()).thenReturn(AC30(Some(10)))
    when(mockBoxRetriever.ac31()).thenReturn(AC31(Some(10)))
    when(mockBoxRetriever.ac34()).thenReturn(AC34(Some(10)))
    when(mockBoxRetriever.ac35()).thenReturn(AC35(Some(10)))
    when(mockBoxRetriever.ac36()).thenReturn(AC36(Some(10)))
    when(mockBoxRetriever.ac37()).thenReturn(AC37(Some(10)))
    when(mockBoxRetriever.ac5032()).thenReturn(AC5032(Some("asd")))
  }

  "ACQ8161 validate" should {
    "return errors when filing is for CoHo and ACQ8161 is empty" in {
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))

      ACQ8161(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("ACQ8161"), "error.ACQ8161.required"))
    }

    "not return errors when filing is for CoHo and ACQ8161 is true" in {
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))

      ACQ8161(Some(true)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is for CoHo and ACQ8161 is false" in {
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.ac16()).thenReturn(AC16(None))
      when(mockBoxRetriever.ac17()).thenReturn(AC17(None))
      when(mockBoxRetriever.ac18()).thenReturn(AC18(None))
      when(mockBoxRetriever.ac19()).thenReturn(AC19(None))
      when(mockBoxRetriever.ac20()).thenReturn(AC20(None))
      when(mockBoxRetriever.ac21()).thenReturn(AC21(None))
      when(mockBoxRetriever.ac26()).thenReturn(AC26(None))
      when(mockBoxRetriever.ac27()).thenReturn(AC27(None))
      when(mockBoxRetriever.ac28()).thenReturn(AC28(None))
      when(mockBoxRetriever.ac29()).thenReturn(AC29(None))
      when(mockBoxRetriever.ac30()).thenReturn(AC30(None))
      when(mockBoxRetriever.ac31()).thenReturn(AC31(None))
      when(mockBoxRetriever.ac34()).thenReturn(AC34(None))
      when(mockBoxRetriever.ac35()).thenReturn(AC35(None))
      when(mockBoxRetriever.ac36()).thenReturn(AC36(None))
      when(mockBoxRetriever.ac37()).thenReturn(AC37(None))
      when(mockBoxRetriever.ac5032()).thenReturn(AC5032(None))

      ACQ8161(Some(false)).validate(mockBoxRetriever) shouldBe Set()
    }

    "return 'cannot exist' error when filing is for CoHo and ACQ8161 is false" in {
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))

      ACQ8161(Some(false)).validate(mockBoxRetriever) shouldBe Set(CtValidation(None, "error.profitAndLoss.cannot.exist"))
    }

    "not return errors when filing is not for CoHo and ACQ8161 is empty" in {
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))

      ACQ8161(None).validate(mockBoxRetriever) shouldBe Set()
    }
  }
}
