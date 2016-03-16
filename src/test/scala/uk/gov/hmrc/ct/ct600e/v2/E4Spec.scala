/*
 * Copyright 2016 HM Revenue & Customs
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
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

class E4Spec extends WordSpec with MockitoSugar with Matchers  {

  val boxRetriever = mock[CT600EBoxRetriever]

  "E4 validation" should {
    "make E4 required when E2 < E1" in {
      when(boxRetriever.retrieveE1()).thenReturn(E1(Some(2)))
      when(boxRetriever.retrieveE2()).thenReturn(E2(Some(1)))
      E4(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("E4"), "error.E4.conditionalRequired", None))
    }
    "make E4 required when E2 empty but E1 is set" in {
      when(boxRetriever.retrieveE1()).thenReturn(E1(Some(1)))
      when(boxRetriever.retrieveE2()).thenReturn(E2(None))
      E4(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("E4"), "error.E4.conditionalRequired", None))
    }

    "enforce E4 empty when E2 > E1" in {
      when(boxRetriever.retrieveE1()).thenReturn(E1(Some(1)))
      when(boxRetriever.retrieveE2()).thenReturn(E2(Some(2)))
      E4(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E4"), "error.E4.conditionalMustBeEmpty"))
    }
    "make E4 not required when E2 set but E1 empty" in {
      when(boxRetriever.retrieveE1()).thenReturn(E1(None))
      when(boxRetriever.retrieveE2()).thenReturn(E2(Some(2)))
      E4(None).validate(boxRetriever) shouldBe Set()
    }
    "make E4 not required when E2 and E1 are both empty" in {
      when(boxRetriever.retrieveE1()).thenReturn(E1(None))
      when(boxRetriever.retrieveE2()).thenReturn(E2(None))
      E4(None).validate(boxRetriever) shouldBe Set()
    }

    "E4 invalid if number less that 1" in {
      when(boxRetriever.retrieveE1()).thenReturn(E1(None))
      when(boxRetriever.retrieveE2()).thenReturn(E2(None))
      E4(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E4"), "error.E4.outOfRange", None))
    }
    "E4 valid if number 1 or greater" in {
      when(boxRetriever.retrieveE1()).thenReturn(E1(None))
      when(boxRetriever.retrieveE2()).thenReturn(E2(None))
      E4(Some(1)).validate(boxRetriever) shouldBe Set()
    }
  }


}
