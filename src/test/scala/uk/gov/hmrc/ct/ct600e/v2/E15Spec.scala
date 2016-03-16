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
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600e.v2.retriever.CT600EBoxRetriever

class E15Spec extends WordSpec with MockitoSugar with Matchers  {

  val boxRetriever = mock[CT600EBoxRetriever]

  "E15 validation" should {
    "throw validation error when E7 > 0 and E15 < 0" in {
      when(boxRetriever.retrieveE7()).thenReturn(E7(Some(1)))
      E15(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E15"), "error.E15.mustBeZeroOrPositive"))
    }

    "don't throw validation error when E7 > 0 and E15 = 0" in {
      when(boxRetriever.retrieveE7()).thenReturn(E7(Some(1)))
      E15(Some(0)).validate(boxRetriever) shouldBe Set()
    }

    "don't throw validation error when E7 > 0 and E15 > 0" in {
      when(boxRetriever.retrieveE7()).thenReturn(E7(Some(1)))
      E15(Some(10)).validate(boxRetriever) shouldBe Set()
    }

    "don't throw validation error when E7 > 0 and E15 = None" in {
      when(boxRetriever.retrieveE7()).thenReturn(E7(Some(1)))
      E15(None).validate(boxRetriever) shouldBe Set()
    }

    "throw validation error when E7 = 0 and E15 != None" in {
      when(boxRetriever.retrieveE7()).thenReturn(E7(Some(0)))
      E15(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E15"), "error.E15.conditionalMustBeEmpty"))
    }

    "don't throw validation error when E7 = 0 and E15 = None" in {
      when(boxRetriever.retrieveE7()).thenReturn(E7(Some(0)))
      E15(None).validate(boxRetriever) shouldBe Set()
    }
  }

}
