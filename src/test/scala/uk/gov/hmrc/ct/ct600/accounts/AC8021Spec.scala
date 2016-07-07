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
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.{AC8021, AC8023}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xAccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.{CompaniesHouseFiling, HMRCFiling, MicroEntityFiling}


class AC8021Spec extends WordSpec with MockitoSugar with Matchers {

  private trait TestBoxRetriever extends Frs10xAccountsBoxRetriever with FilingAttributesBoxValueRetriever

  "AC8021 validate" should {
    "return errors when filing is for CoHo and AC8021 is empty" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.retrieveCompaniesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.retrieveHMRCFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.retrieveMicroEntityFiling()).thenReturn(MicroEntityFiling(false))
      when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(Some(false)))

      AC8021(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8021"), "error.AC8021.required"))
    }

    "not return errors when filing is for CoHo and AC8021 is true" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.retrieveCompaniesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.retrieveHMRCFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.retrieveMicroEntityFiling()).thenReturn(MicroEntityFiling(false))
      when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(Some(false)))

      AC8021(Some(true)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is for CoHo and AC8021 is false" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.retrieveCompaniesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.retrieveHMRCFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.retrieveMicroEntityFiling()).thenReturn(MicroEntityFiling(false))
      when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(Some(false)))

      AC8021(Some(false)).validate(mockBoxRetriever) shouldBe Set()
    }

    "return errors when filing is for Joint and AC8021 is empty" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.retrieveCompaniesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.retrieveHMRCFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.retrieveMicroEntityFiling()).thenReturn(MicroEntityFiling(false))
      when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(Some(false)))

      AC8021(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8021"), "error.AC8021.required"))
    }

    "not return errors when filing is for Joint and AC8021 is true" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.retrieveCompaniesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.retrieveHMRCFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.retrieveMicroEntityFiling()).thenReturn(MicroEntityFiling(false))
      when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(Some(false)))

      AC8021(Some(true)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is for Joint and AC8021 is false" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.retrieveCompaniesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.retrieveHMRCFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.retrieveMicroEntityFiling()).thenReturn(MicroEntityFiling(false))
      when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(Some(false)))

      AC8021(Some(false)).validate(mockBoxRetriever) shouldBe Set()
    }

    "return errors when filing is for Joint micro-entity, AC8023=true and AC8021 is empty" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.retrieveCompaniesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.retrieveHMRCFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.retrieveMicroEntityFiling()).thenReturn(MicroEntityFiling(true))
      when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(Some(true)))

      AC8021(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8021"), "error.AC8021.required"))
    }

    "not return errors when filing is for Joint micro-entity, AC8023=false and AC8021 is empty" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.retrieveCompaniesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.retrieveHMRCFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.retrieveMicroEntityFiling()).thenReturn(MicroEntityFiling(true))
      when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(Some(false)))

      AC8021(None).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is for Joint micro-entity, AC8023=true and AC8021 is true" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.retrieveCompaniesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.retrieveHMRCFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.retrieveMicroEntityFiling()).thenReturn(MicroEntityFiling(true))
      when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(Some(true)))

      AC8021(Some(true)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is for Joint micro-entity, AC8023=true and AC8021 is false" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.retrieveCompaniesHouseFiling()).thenReturn(CompaniesHouseFiling(true))
      when(mockBoxRetriever.retrieveHMRCFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.retrieveMicroEntityFiling()).thenReturn(MicroEntityFiling(true))
      when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(Some(true)))

      AC8021(Some(false)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is for HMRC and AC8021 is empty" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.retrieveCompaniesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
      when(mockBoxRetriever.retrieveHMRCFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.retrieveMicroEntityFiling()).thenReturn(MicroEntityFiling(false))
      when(mockBoxRetriever.retrieveAC8023()).thenReturn(AC8023(Some(false)))

      AC8021(None).validate(mockBoxRetriever) shouldBe Set()
    }
  }
}
