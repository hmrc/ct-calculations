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
import uk.gov.hmrc.ct.{CompaniesHouseFiling, HMRCFiling, MicroEntityFiling}


class AC8023Spec extends WordSpec with MockitoSugar with Matchers {

  private trait TestBoxRetriever extends Frs10xDirectorsBoxRetriever with FilingAttributesBoxValueRetriever

  "AC8023 validate" should {
    "return errors when micro-entity filing is for HMRC and AC8023 is empty" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))

      AC8023(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8023"), "error.AC8023.required"))
    }

    "return 'cannot exist' errors when filing is for Hmrc Only Micro and AC8023 is false" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.directors()).thenReturn(Directors(List(Director("3", "Chuck"))))
      when(mockBoxRetriever.companiesHouseFiling()).thenReturn(CompaniesHouseFiling(false))
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))

      AC8023(Some(false)).validate(mockBoxRetriever) shouldBe Set(CtValidation(None, "error.AC8023.directorsReport.cannot.exist"))
    }

    "not return errors when micro-entity filing is for HMRC and AC8023 is true" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))

      AC8023(Some(true)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when micro-entity filing is for HMRC and AC8023 is false" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))
      when(mockBoxRetriever.directors()).thenReturn(Directors())
      when(mockBoxRetriever.acQ8003()).thenReturn(ACQ8003(None))
      when(mockBoxRetriever.ac8033()).thenReturn(AC8033(None))
      when(mockBoxRetriever.acQ8009()).thenReturn(ACQ8009(None))
      when(mockBoxRetriever.ac8051()).thenReturn(AC8051(None))
      when(mockBoxRetriever.ac8052()).thenReturn(AC8052(None))
      when(mockBoxRetriever.ac8053()).thenReturn(AC8053(None))
      when(mockBoxRetriever.ac8054()).thenReturn(AC8054(None))
      when(mockBoxRetriever.ac8899()).thenReturn(AC8899(None))

      AC8023(Some(false)).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is not micro-entity for HMRC and AC8023 is empty" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(true))
      when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))

      AC8023(None).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is micro-entity and not for HMRC and AC8023 is empty" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(true))

      AC8023(None).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when filing is not micro-entity and not for HMRC and AC8023 is empty" in {
      val mockBoxRetriever = mock[TestBoxRetriever]
      when(mockBoxRetriever.hmrcFiling()).thenReturn(HMRCFiling(false))
      when(mockBoxRetriever.microEntityFiling()).thenReturn(MicroEntityFiling(false))

      AC8023(None).validate(mockBoxRetriever) shouldBe Set()
    }
  }
}
