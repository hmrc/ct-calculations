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

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.{reset, when}
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP263Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter {

  val mockRetriever = mock[ComputationsBoxRetriever]

  before {
    reset(mockRetriever)
  }

  "CP263" should {
    "be None if CP283b and CP997 are both None" in {
      when(mockRetriever.cp283b()).thenReturn(CP283b(None))
      when(mockRetriever.cp997()).thenReturn(CP997(None))
      CP263.calculate(mockRetriever) shouldBe CP263(None)
    }
    "be CP283b if CP997 is None" in {
      when(mockRetriever.cp283b()).thenReturn(CP283b(Some(10)))
      when(mockRetriever.cp997()).thenReturn(CP997(None))
      CP263.calculate(mockRetriever) shouldBe CP263(Some(10))
    }
    "be CP997 if CP283b is None" in {
      when(mockRetriever.cp283b()).thenReturn(CP283b(None))
      when(mockRetriever.cp997()).thenReturn(CP997(Some(10)))
      CP263.calculate(mockRetriever) shouldBe CP263(Some(10))
    }
    "be CP997 + CP283b if both are defined" in {
      when(mockRetriever.cp283b()).thenReturn(CP283b(Some(10)))
      when(mockRetriever.cp997()).thenReturn(CP997(Some(10)))
      CP263.calculate(mockRetriever) shouldBe CP263(Some(20))
    }
  }

}
