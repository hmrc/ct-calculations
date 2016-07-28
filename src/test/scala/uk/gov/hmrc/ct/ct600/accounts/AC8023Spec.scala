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
import uk.gov.hmrc.ct.{CompaniesHouseFiling, HMRCFiling, MicroEntityFiling}
import uk.gov.hmrc.ct.accounts.frs10x.AC8023
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever


class AC8023Spec extends WordSpec with MockitoSugar with Matchers {

  "AC8023 validate" should {
    "return errors when micro-entity filing is for HMRC and AC8023 is empty" in {
      val mockBoxRetriever = mock[FilingAttributesBoxValueRetriever]
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))

      AC8023(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8023"), "error.AC8023.required"))
    }

    "not return errors when micro-entity filing is for HMRC and AC8023 is true" in {
      val mockBoxRetriever = mock[FilingAttributesBoxValueRetriever]
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))

      AC8023(Some(true)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when micro-entity filing is for HMRC and AC8023 is false" in {
      val mockBoxRetriever = mock[FilingAttributesBoxValueRetriever]
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))

      AC8023(Some(false)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is not micro-entity for HMRC and AC8023 is empty" in {
      val mockBoxRetriever = mock[FilingAttributesBoxValueRetriever]
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))

      AC8023(None).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is micro-entity and not for HMRC and AC8023 is empty" in {
      val mockBoxRetriever = mock[FilingAttributesBoxValueRetriever]
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))

      AC8023(None).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is not micro-entity and not for HMRC and AC8023 is empty" in {
      val mockBoxRetriever = mock[FilingAttributesBoxValueRetriever]
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))

      AC8023(None).validate(mockBoxRetriever) shouldBe Set()
    }
  }
}
