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

package uk.gov.hmrc.ct.accounts.frs102

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC8021, AC8023, ACQ8009}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDirectorsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.{CompaniesHouseFiling, HMRCFiling, MicroEntityFiling}

class ACQ8009Spec extends WordSpec with Matchers with MockitoSugar with BeforeAndAfterEach {

  val mockBoxRetriever = mock[MockableFrs10xBoxretrieverWithFilingAttributes]

  override def beforeEach = {
    DirectorsMockSetup.setupDefaults(mockBoxRetriever)
  }


  "ACQ8009 should" should {

    "validate successfully when no errors present" in {

      val secretary = ACQ8009(Some(true))

      secretary.validate(mockBoxRetriever) shouldBe empty
    }

    "validate as mandatory" in {

      val secretary = ACQ8009(None)

      secretary.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("ACQ8009"), "error.ACQ8009.required", None))
    }

    "no validate if no directors report" in {

      when(mockBoxRetriever.ac8021()).thenReturn(AC8021(None))
      when(mockBoxRetriever.ac8023()).thenReturn(AC8023(Some(false)))

      val secretary = ACQ8009(None)

      secretary.validate(mockBoxRetriever) shouldBe empty
    }
  }
}
