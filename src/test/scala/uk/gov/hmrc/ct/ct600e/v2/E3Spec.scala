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
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

class E3Spec extends WordSpec with MockitoSugar with Matchers  {

  val boxRetriever = mock[CT600EBoxRetriever]

  "E3 validation" should {
    "make E3 required when E2 > E1" in {
      when(boxRetriever.e1()).thenReturn(E1(Some(1)))
      when(boxRetriever.e2()).thenReturn(E2(Some(2)))
      E3(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("E3"), "error.E3.conditionalRequired", None))
    }
    "make E3 required when E2 set but E1 is empty" in {
      when(boxRetriever.e1()).thenReturn(E1(None))
      when(boxRetriever.e2()).thenReturn(E2(Some(2)))
      E3(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("E3"), "error.E3.conditionalRequired", None))
    }

    "enforce E3 empty when E2 < E1" in {
      when(boxRetriever.e1()).thenReturn(E1(Some(2)))
      when(boxRetriever.e2()).thenReturn(E2(Some(1)))
      E3(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E3"), "error.E3.conditionalMustBeEmpty", None))
    }
    "make E3 not required when E2 empty but E1 set" in {
      when(boxRetriever.e1()).thenReturn(E1(Some(1)))
      when(boxRetriever.e2()).thenReturn(E2(None))
      E3(None).validate(boxRetriever) shouldBe Set()
    }

    "make E3 not required when E2 and E1 are both empty" in {
      when(boxRetriever.e1()).thenReturn(E1(None))
      when(boxRetriever.e2()).thenReturn(E2(None))
      E3(None).validate(boxRetriever) shouldBe Set()
    }

    "E3 invalid if number less that 1" in {
      when(boxRetriever.e1()).thenReturn(E1(None))
      when(boxRetriever.e2()).thenReturn(E2(None))
      E3(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E3"), "error.E3.outOfRange", None))
    }
    "E3 valid if number 1 or greater" in {
      when(boxRetriever.e1()).thenReturn(E1(None))
      when(boxRetriever.e2()).thenReturn(E2(None))
      E3(Some(1)).validate(boxRetriever) shouldBe Set()
    }
  }


}
