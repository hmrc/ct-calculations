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

package uk.gov.hmrc.ct.ct600e.v3

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

class E170Spec extends WordSpec with Matchers with MockitoSugar {

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
