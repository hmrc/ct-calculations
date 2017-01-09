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

package uk.gov.hmrc.ct.ct600e.v2

import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

class E1012Spec extends WordSpec with MockitoSugar with Matchers {


  "E1012 calculated from E1011" should {
    "be None if E1011 is None" in {
      val boxRetriever = mock[CT600EBoxRetriever]
      when(boxRetriever.e1011()).thenReturn(E1011(None))
      E1012.calculate(boxRetriever) shouldBe E1012(None)
    }
    "be true if E1011 is false" in {
      val boxRetriever = mock[CT600EBoxRetriever]
      when(boxRetriever.e1011()).thenReturn(E1011(Some(false)))
      E1012.calculate(boxRetriever) shouldBe E1012(Some(true))
    }
    "be false if E1011 is true" in {
      val boxRetriever = mock[CT600EBoxRetriever]
      when(boxRetriever.e1011()).thenReturn(E1011(Some(true)))
      E1012.calculate(boxRetriever) shouldBe E1012(Some(false))
    }
  }

}
