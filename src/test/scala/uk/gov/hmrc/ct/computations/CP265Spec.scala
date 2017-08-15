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

import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP265Spec extends WordSpec with MockitoSugar with Matchers with BeforeAndAfter {

  val mockRetriever = mock[ComputationsBoxRetriever]

  before {
    reset(mockRetriever)
  }

  "CP265" should {
    "be CP293 with post-reform losses added back in" in {
      when(mockRetriever.cp293()).thenReturn(CP293(3))
      when(mockRetriever.cp283b()).thenReturn(CP283b(2))
      CP265.calculate(mockRetriever) shouldBe CP265(5)
    }
  }
}
