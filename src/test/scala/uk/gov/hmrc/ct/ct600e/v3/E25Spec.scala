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

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import uk.gov.hmrc.ct.ct600e.v3.retriever.CT600EBoxRetriever

class E25Spec extends WordSpec with MockitoSugar with Matchers {


  "E25 calculated from E20" should {
    "be None is E20 is None" in {
      val boxRetriever = mock[CT600EBoxRetriever]
      when(boxRetriever.e20()).thenReturn(E20(None))
      E25.calculate(boxRetriever) shouldBe E25(None)
    }
    "be true is E20 is false" in {
      val boxRetriever = mock[CT600EBoxRetriever]
      when(boxRetriever.e20()).thenReturn(E20(Some(false)))
      E25.calculate(boxRetriever) shouldBe E25(Some(true))
    }
    "be false is E20 is true" in {
      val boxRetriever = mock[CT600EBoxRetriever]
      when(boxRetriever.e20()).thenReturn(E20(Some(true)))
      E25.calculate(boxRetriever) shouldBe E25(Some(false))
    }
  }

}
