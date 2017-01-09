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

package uk.gov.hmrc.ct.ct600.v2

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.ct600.v2.retriever.CT600BoxRetriever
import org.mockito.Mockito._

class B42Spec extends WordSpec with Matchers with MockitoSugar {

  "B42" should {

    "be false if both B42a and B42b are false" in {

      val boxRetriever = mock[CT600BoxRetriever]

      when(boxRetriever.b42a()).thenReturn(B42a(Some(false)))
      when(boxRetriever.b42b()).thenReturn(B42b(Some(false)))

      B42.calculate(boxRetriever).value shouldBe false
    }

    "be false if both B42a and B42b are None" in {

      val boxRetriever = mock[CT600BoxRetriever]

      when(boxRetriever.b42a()).thenReturn(B42a(None))
      when(boxRetriever.b42b()).thenReturn(B42b(None))

      B42.calculate(boxRetriever).value shouldBe false
    }

    "be true if B42a is true" in {

      val boxRetriever = mock[CT600BoxRetriever]

      when(boxRetriever.b42a()).thenReturn(B42a(Some(true)))
      when(boxRetriever.b42b()).thenReturn(B42b(Some(false)))

      B42.calculate(boxRetriever).value shouldBe true
    }

    "be true if B42b is true" in {

      val boxRetriever = mock[CT600BoxRetriever]

      when(boxRetriever.b42a()).thenReturn(B42a(None))
      when(boxRetriever.b42b()).thenReturn(B42b(Some(true)))

      B42.calculate(boxRetriever).value shouldBe true
    }

    "be true if both B42a and B42b is true" in {

      val boxRetriever = mock[CT600BoxRetriever]

      when(boxRetriever.b42a()).thenReturn(B42a(Some(true)))
      when(boxRetriever.b42b()).thenReturn(B42b(Some(true)))

      B42.calculate(boxRetriever).value shouldBe true
    }

  }

}
