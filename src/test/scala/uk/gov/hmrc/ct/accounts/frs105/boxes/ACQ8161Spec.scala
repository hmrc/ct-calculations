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

package uk.gov.hmrc.ct.accounts.frs105.boxes

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs105.retriever.Frs105AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.{CompaniesHouseFiling, HMRCFiling}


class ACQ8161Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfterEach {

  private trait TestBoxRetriever extends Frs105AccountsBoxRetriever with FilingAttributesBoxValueRetriever

  private var mockBoxRetriever: TestBoxRetriever = _

  override protected def beforeEach(): Unit = {
    super.beforeEach()

    mockBoxRetriever = mock[TestBoxRetriever]

    when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
    when(mockBoxRetriever.ac405()).thenReturn(AC405(Some(10)))
    when(mockBoxRetriever.ac406()).thenReturn(AC406(Some(10)))
    when(mockBoxRetriever.ac410()).thenReturn(AC410(Some(10)))
    when(mockBoxRetriever.ac411()).thenReturn(AC411(Some(10)))
    when(mockBoxRetriever.ac415()).thenReturn(AC415(Some(10)))
    when(mockBoxRetriever.ac416()).thenReturn(AC416(Some(10)))
    when(mockBoxRetriever.ac420()).thenReturn(AC420(Some(10)))
    when(mockBoxRetriever.ac421()).thenReturn(AC421(Some(10)))
    when(mockBoxRetriever.ac425()).thenReturn(AC425(Some(10)))
    when(mockBoxRetriever.ac426()).thenReturn(AC426(Some(10)))
    when(mockBoxRetriever.ac34()).thenReturn(AC34(Some(10)))
    when(mockBoxRetriever.ac35()).thenReturn(AC35(Some(10)))
  }

  "ACQ8161 validate" should {
    "return errors when filing is for CoHo and ACQ8161 is empty" in {
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))

      ACQ8161(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("ACQ8161"), "error.ACQ8161.required"))
    }

    "not return errors when filing is not CoHo and ACQ8161 is empty" in {
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))

      ACQ8161(None).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is for CoHo and ACQ8161 is true" in {
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))

      ACQ8161(Some(true)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is Joint and ACQ8161 is true" in {
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))

      ACQ8161(Some(true)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is CoHo only and ACQ8161 is false" in {
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.ac405()).thenReturn(AC405(None))
      when(mockBoxRetriever.ac406()).thenReturn(AC406(None))
      when(mockBoxRetriever.ac410()).thenReturn(AC410(None))
      when(mockBoxRetriever.ac411()).thenReturn(AC411(None))
      when(mockBoxRetriever.ac415()).thenReturn(AC415(None))
      when(mockBoxRetriever.ac416()).thenReturn(AC416(None))
      when(mockBoxRetriever.ac420()).thenReturn(AC420(None))
      when(mockBoxRetriever.ac421()).thenReturn(AC421(None))
      when(mockBoxRetriever.ac425()).thenReturn(AC425(None))
      when(mockBoxRetriever.ac426()).thenReturn(AC426(None))
      when(mockBoxRetriever.ac34()).thenReturn(AC34(None))
      when(mockBoxRetriever.ac35()).thenReturn(AC35(None))

      ACQ8161(Some(false)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is Joint and ACQ8161 is false" in {
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))

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
