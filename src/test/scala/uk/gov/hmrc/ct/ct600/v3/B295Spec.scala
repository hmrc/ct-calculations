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

package uk.gov.hmrc.ct.ct600.v3

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever

class B295Spec extends AnyWordSpec with MockitoSugar with Matchers {

  "B295" should {
    "equal B275 + B285" in {
      val retriever = mock[CT600BoxRetriever]
      when(retriever.b275()).thenReturn(B275(1))
      when(retriever.b285()).thenReturn(B285(1))
      B295.calculate(retriever) shouldBe B295(2)
    }
  }
}
