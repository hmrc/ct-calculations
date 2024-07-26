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

package uk.gov.hmrc.ct.ct600e.v3

import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

class E170Spec extends AnyWordSpec with Matchers with MockitoSugar {

  val boxRetriever = mock[CT600EBoxRetriever]
  "E170" should {
    "calculate" when {
      "both E170A and E170B has a value" in {
        when(boxRetriever.e170A()).thenReturn(E170A(Some(337)))
        when(boxRetriever.e170B()).thenReturn(E170B(Some(1000)))
        E170.calculate(boxRetriever) shouldBe E170(Some(1337))
      }
      "either E170A or E170B has a value" in {
        when(boxRetriever.e170A()).thenReturn(E170A(Some(337)))
        when(boxRetriever.e170B()).thenReturn(E170B(None))
        E170.calculate(boxRetriever) shouldBe E170(Some(337))
      }
    }
    "return none" when{
      "both E170A and E170B is None" in {
        when(boxRetriever.e170A()).thenReturn(E170A(None))
        when(boxRetriever.e170B()).thenReturn(E170B(None))
        E170.calculate(boxRetriever) shouldBe E170(None)
      }
    }
  }
}
