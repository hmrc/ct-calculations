/*
 * Copyright 2024 HM Revenue & Customs
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
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.frs10x.boxes.{AC8021, AC8023, ACQ8003}
import uk.gov.hmrc.ct.box.CtValidation

class ACQ8003Spec extends AnyWordSpec with Matchers with MockitoSugar with BeforeAndAfterEach {

  val mockBoxRetriever = mock[MockableFrs10xBoxretrieverWithFilingAttributes]

  override def beforeEach = {
    DirectorsMockSetup.setupDefaults(mockBoxRetriever)
  }

  "AC8033 should" should {

    "validate successfully when no errors present" in {

      val secretary = ACQ8003(Some(true))

      secretary.validate(mockBoxRetriever) shouldBe empty
    }

    "validate as mandatory" in {

      val secretary = ACQ8003(None)

      secretary.validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("ACQ8003"), "error.ACQ8003.required", None))
    }

    "no validate if no directors report" in {

      when(mockBoxRetriever.ac8021()).thenReturn(AC8021(None))
      when(mockBoxRetriever.ac8023()).thenReturn(AC8023(Some(false)))

      val secretary = ACQ8003(None)

      secretary.validate(mockBoxRetriever) shouldBe empty
    }
  }
}
