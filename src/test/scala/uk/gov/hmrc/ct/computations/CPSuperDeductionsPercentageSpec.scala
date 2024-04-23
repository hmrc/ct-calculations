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

import java.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.accounts.{AC5, AC6}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CPSuperDeductionsPercentageSpec extends AnyWordSpec with MockitoSugar with Matchers with BeforeAndAfter {

  val mockRetriever = mock[ComputationsBoxRetriever]

  before {
    reset(mockRetriever)
  }

  "CPSuperDeductionPercentage" should {
    "calculate correct percentage of super deductions" in {
      when(mockRetriever.ac5()).thenReturn(AC5(LocalDate.of(2021,4,1)))
      when(mockRetriever.ac6()).thenReturn(AC6(LocalDate.of(2023,3,31)))
      when(mockRetriever.cp1()).thenReturn(CP1(LocalDate.of(2022,10,1)))
      when(mockRetriever.cp2()).thenReturn(CP2(LocalDate.of(2023,9,30)))
      CPSuperDeductionPercentage.calculate(mockRetriever) shouldBe CPSuperDeductionPercentage(1.14959)
    }
  }
}
