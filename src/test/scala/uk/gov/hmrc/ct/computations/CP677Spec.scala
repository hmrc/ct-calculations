/*
 * Copyright 2022 HM Revenue & Customs
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
import org.mockito.Mockito.{reset, when}
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP677Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter {

  val boxRetriever = mock[ComputationsBoxRetriever]

  before {
    reset(boxRetriever)
  }

  "CP677" should{
    "Calculate the correct value" in {
      when(boxRetriever.cp1()).thenReturn(CP1(new LocalDate(2022,10,1)))
      when(boxRetriever.cp2()).thenReturn(CP2(new LocalDate(2023,9,30)))
      when(boxRetriever.cp675()).thenReturn(CP675(Some(100)))
      CP677.calculate(boxRetriever) shouldBe CP677(Some(115))
    }
    "Calculate the correct value when CP675 is 0" in {
      when(boxRetriever.cp1()).thenReturn(CP1(new LocalDate(2022,10,1)))
      when(boxRetriever.cp2()).thenReturn(CP2(new LocalDate(2023,9,30)))
      when(boxRetriever.cp675()).thenReturn(CP675(Some(0)))
      CP677.calculate(boxRetriever) shouldBe CP677(Some(0))
    }
    "Calculate correct value when CP675 is not present" in {
      when(boxRetriever.cp1()).thenReturn(CP1(new LocalDate(2022,10,1)))
      when(boxRetriever.cp2()).thenReturn(CP2(new LocalDate(2023,9,30)))
      when(boxRetriever.cp675()).thenReturn(CP675(None))
      CP677.calculate(boxRetriever) shouldBe CP677(None)
    }
    "Not Calculate when dates outside super deduction period" in {
      when(boxRetriever.cp1()).thenReturn(CP1(new LocalDate(1993,10,1)))
      when(boxRetriever.cp2()).thenReturn(CP2(new LocalDate(1994,9,30)))
      when(boxRetriever.cp675()).thenReturn(CP675(None))
      CP677.calculate(boxRetriever) shouldBe CP677(None)
    }
  }
}
