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
import org.mockito.Mockito.when
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.utils.UnitSpec

class CP679Spec extends UnitSpec {



  "CP679" should {
    "Return CP677 - CP678" in {
      when(mockComputationsBoxRetriever.cp1()).thenReturn(CP1(new LocalDate(2022,4,1)))
      when(mockComputationsBoxRetriever.cp2()).thenReturn(CP2(new LocalDate(2023,3,31)))
      when(mockComputationsBoxRetriever.cp677()).thenReturn(CP677(Option(110)))
      when(mockComputationsBoxRetriever.cp678()).thenReturn(CP678(Option(100)))
      CP679.calculate(mockComputationsBoxRetriever) shouldBe CP679(Some(10))
    }
    "return None" in {
      when(mockComputationsBoxRetriever.cp1()).thenReturn(CP1(new LocalDate(2022,4,1)))
      when(mockComputationsBoxRetriever.cp2()).thenReturn(CP2(new LocalDate(2023,3,31)))
      when(mockComputationsBoxRetriever.cp677()).thenReturn(CP677(Option(100)))
      when(mockComputationsBoxRetriever.cp678()).thenReturn(CP678(Option(110)))
      CP679.calculate(mockComputationsBoxRetriever) shouldBe CP679(None)
    }
  }
}
