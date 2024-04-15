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

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.{reset, when}
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import java.time.LocalDate
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP678Spec  extends AnyWordSpec with MockitoSugar with Matchers with BeforeAndAfter {

  val mockRetriever = mock[ComputationsBoxRetriever]

  before {
    reset(mockRetriever)
  }


  "CP677" should {
    "be CP677 with associate percentage of 675 for period of superdeduction" in {
      when(mockRetriever.cp676()).thenReturn(CP676(200))
      when(mockRetriever.cp1()).thenReturn(CP1(LocalDate.of(2022,4,1)))
      when(mockRetriever.cp2()).thenReturn(CP2(LocalDate.of(2023,3,31)))
      CP678.calculate(mockRetriever) shouldBe CP678(Some(260))
    }
    "Calculate the correct value" in {
      when(mockRetriever.cp1()).thenReturn(CP1(LocalDate.of(2022,10,1)))
      when(mockRetriever.cp2()).thenReturn(CP2(LocalDate.of(2023,9,30)))
      when(mockRetriever.cp676()).thenReturn(CP676(Some(100)))
      CP678.calculate(mockRetriever) shouldBe CP678(Some(114))
    }
    "Calculate the correct value when CP676 is 0" in {
      when(mockRetriever.cp1()).thenReturn(CP1(LocalDate.of(2022,10,1)))
      when(mockRetriever.cp2()).thenReturn(CP2(LocalDate.of(2023,9,30)))
      when(mockRetriever.cp676()).thenReturn(CP676(Some(0)))
      CP678.calculate(mockRetriever) shouldBe CP678(Some(0))
    }
    "Calculate correct value when CP676 is not present" in {
      when(mockRetriever.cp1()).thenReturn(CP1(LocalDate.of(2022,10,1)))
      when(mockRetriever.cp2()).thenReturn(CP2(LocalDate.of(2023,9,30)))
      when(mockRetriever.cp676()).thenReturn(CP676(None))
      CP678.calculate(mockRetriever) shouldBe CP678(None)
    }
    "Not Calculate when dates outside super deduction period" in {
      when(mockRetriever.cp1()).thenReturn(CP1(LocalDate.of(1993,10,1)))
      when(mockRetriever.cp2()).thenReturn(CP2(LocalDate.of(1994,9,30)))
      when(mockRetriever.cp676()).thenReturn(CP676(None))
      CP678.calculate(mockRetriever) shouldBe CP678(None)
    }
  }

}
