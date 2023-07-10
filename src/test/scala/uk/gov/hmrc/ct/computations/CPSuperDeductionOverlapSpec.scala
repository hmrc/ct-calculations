/*
 * Copyright 2023 HM Revenue & Customs
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

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.{AC5, AC6}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CPSuperDeductionOverlapSpec extends AnyWordSpec with MockitoSugar with Matchers with BeforeAndAfter {

  val mockRetriever = mock[ComputationsBoxRetriever]

  before {
    reset(mockRetriever)
  }

  "CPSuperDeductionOverlap" should {
    "calculate overlap is true" in {
      when(mockRetriever.ac5()).thenReturn(AC5(new LocalDate(2021,4,1)))
      when(mockRetriever.ac6()).thenReturn(AC6(new LocalDate(2023,3,31)))
      when(mockRetriever.cp1()).thenReturn(CP1(new LocalDate(2022,10,1)))
      when(mockRetriever.cp2()).thenReturn(CP2(new LocalDate(2023,9,30)))
      CPSuperDeductionOverlap.calculate(mockRetriever) shouldBe CPSuperDeductionOverlap(true)
    }

    "calculate overlap is true 2" in {
      when(mockRetriever.ac5()).thenReturn(AC5(new LocalDate(2021,4,1)))
      when(mockRetriever.ac6()).thenReturn(AC6(new LocalDate(2023,3,31)))
      when(mockRetriever.cp1()).thenReturn(CP1(new LocalDate(2020,10,1)))
      when(mockRetriever.cp2()).thenReturn(CP2(new LocalDate(2021,9,30)))
      CPSuperDeductionOverlap.calculate(mockRetriever) shouldBe CPSuperDeductionOverlap(true)
    }

    "calculate overlap is true 3" in {
      when(mockRetriever.ac5()).thenReturn(AC5(new LocalDate(2021,4,1)))
      when(mockRetriever.ac6()).thenReturn(AC6(new LocalDate(2023,3,31)))
      when(mockRetriever.cp1()).thenReturn(CP1(new LocalDate(2021,10,1)))
      when(mockRetriever.cp2()).thenReturn(CP2(new LocalDate(2022,9,30)))
      CPSuperDeductionOverlap.calculate(mockRetriever) shouldBe CPSuperDeductionOverlap(true)
    }

    "calculate overlap is false" in {
      when(mockRetriever.ac5()).thenReturn(AC5(new LocalDate(2021,4,1)))
      when(mockRetriever.ac6()).thenReturn(AC6(new LocalDate(2023,3,31)))
      when(mockRetriever.cp1()).thenReturn(CP1(new LocalDate(2019,10,1)))
      when(mockRetriever.cp2()).thenReturn(CP2(new LocalDate(2020,9,30)))
      CPSuperDeductionOverlap.calculate(mockRetriever) shouldBe CPSuperDeductionOverlap(false)
    }

    "calculate overlap is false 2" in {
      when(mockRetriever.ac5()).thenReturn(AC5(new LocalDate(2021,4,1)))
      when(mockRetriever.ac6()).thenReturn(AC6(new LocalDate(2023,3,31)))
      when(mockRetriever.cp1()).thenReturn(CP1(new LocalDate(2023,10,1)))
      when(mockRetriever.cp2()).thenReturn(CP2(new LocalDate(2024,9,30)))
      CPSuperDeductionOverlap.calculate(mockRetriever) shouldBe CPSuperDeductionOverlap(false)
    }
  }
}
