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
import org.mockito.Mockito._
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.BoxRetriever
import uk.gov.hmrc.ct.computations.CP287
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class RSQ2Spec extends WordSpec with Matchers with MockitoSugar {

  "RSQ2 validation" should {

    "return no error if input value populated and CP287 has no value" in {
      val boxRetriever = mock[ComputationsBoxRetriever]

      when(boxRetriever.cp287()).thenReturn(CP287(None))

      RSQ2(Some(true)).validate(boxRetriever) shouldBe empty
    }

    "return a mandatory error if no input value populated and CP287 has no value" in {
      val boxRetriever = mock[ComputationsBoxRetriever]

      when(boxRetriever.cp287()).thenReturn(CP287(None))

      RSQ2(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("RSQ2"), "error.RSQ2.required"))
    }

    "return a mandatory error if no input value populated and CP287 has value 0" in {
      val boxRetriever = mock[ComputationsBoxRetriever]

      when(boxRetriever.cp287()).thenReturn(CP287(Some(0)))

      RSQ2(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("RSQ2"), "error.RSQ2.required"))
    }

    "return a mandatory error if no input value populated, and not a computations box retriever" in {
      val boxRetriever = mock[BoxRetriever]

      RSQ2(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("RSQ2"), "error.RSQ2.required"))
    }

    "return no error if input value populated, and not a computations box retriever" in {
      val boxRetriever = mock[BoxRetriever]

      RSQ2(Some(false)).validate(boxRetriever) shouldBe empty
    }

    "return a box should not exist error if there is an input value populated and CP287 has a value" in {
      val boxRetriever = mock[ComputationsBoxRetriever]

      when(boxRetriever.cp287()).thenReturn(CP287(Some(10)))

      RSQ2(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("RSQ2"), "error.RSQ2.cannot.exist"))
    }

    "return no error if there is no input value populated and CP287 has a value" in {
      val boxRetriever = mock[ComputationsBoxRetriever]

      when(boxRetriever.cp287()).thenReturn(CP287(Some(10)))

      RSQ2(None).validate(boxRetriever) shouldBe empty
    }

    "return no error if there is an input value populated and CP287 has a value of 0" in {
      val boxRetriever = mock[ComputationsBoxRetriever]

      when(boxRetriever.cp287()).thenReturn(CP287(Some(0)))

      RSQ2(Some(false)).validate(boxRetriever) shouldBe empty
    }

  }

}
