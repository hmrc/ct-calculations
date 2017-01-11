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

package uk.gov.hmrc.ct.ct600.accounts

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{Directors, _}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.{CompaniesHouseFiling, HMRCFiling, MicroEntityFiling, StatutoryAccountsFiling}


class AC8023Spec extends WordSpec with MockitoSugar with Matchers {

  private trait TestBoxRetriever extends Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever

  "AC8023 validate" should {
    "for HMRC Only micro entity filing" when {
      "return errors when AC8023 is empty" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))

        AC8023(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8023"), "error.AC8023.required"))
      }

      "validate successfully when AC8023 is true" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))

        AC8023(Some(true)).validate(mockBoxRetriever) shouldBe Set()
      }

      "validate successfully when AC8023 is false" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))

        AC8023(Some(false)).validate(mockBoxRetriever) shouldBe Set()
      }
    }

    "for Joint micro entity filing" when {
      "return errors when AC8023 is empty" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))

        AC8023(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8023"), "error.AC8023.required"))
      }

      "validate successfully when AC8023 is true" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))

        AC8023(Some(true)).validate(mockBoxRetriever) shouldBe Set()
      }

      "validate successfully when AC8023 is false" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))

        AC8023(Some(false)).validate(mockBoxRetriever) shouldBe Set()
      }
    }

    "for CoHo Only micro entity filing" when {
      "validate successfully when AC8023 is empty" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))

        AC8023(None).validate(mockBoxRetriever) shouldBe Set()
      }

      "cannot exist when AC8023 is true" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))

        AC8023(Some(true)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8023"), "error.AC8023.cannot.exist"))
      }

      "cannot exist when AC8023 is false" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(false))

        AC8023(Some(false)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8023"), "error.AC8023.cannot.exist"))
      }
    }

    "for HMRC Only statutory filing" when {
      "validate successfully when AC8023 is empty" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))

        AC8023(None).validate(mockBoxRetriever) shouldBe Set()
      }

      "validate successfully when AC8023 is true" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))

        AC8023(Some(true)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8023"), "error.AC8023.cannot.exist"))
      }

      "validate successfully when AC8023 is false" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))

        AC8023(Some(false)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8023"), "error.AC8023.cannot.exist"))
      }
    }

    "for Joint statutory filing" when {
      "return errors when AC8023 is empty" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))

        AC8023(None).validate(mockBoxRetriever) shouldBe Set()
      }

      "validate successfully when AC8023 is true" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))

        AC8023(Some(true)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8023"), "error.AC8023.cannot.exist"))
      }

      "validate successfully when AC8023 is false" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))

        AC8023(Some(false)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8023"), "error.AC8023.cannot.exist"))
      }
    }

    "for CoHo Only statutory filing" when {
      "validate successfully when AC8023 is empty" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))

        AC8023(None).validate(mockBoxRetriever) shouldBe Set()
      }

      "cannot exist when AC8023 is true" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))

        AC8023(Some(true)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8023"), "error.AC8023.cannot.exist"))
      }

      "cannot exist when AC8023 is false" in {
        val mockBoxRetriever = mock[TestBoxRetriever]
        when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
        when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
        when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))
        when(mockBoxRetriever.statutoryAccountsFiling()).thenReturn(StatutoryAccountsFiling(true))

        AC8023(Some(false)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8023"), "error.AC8023.cannot.exist"))
      }
    }
  }
}
