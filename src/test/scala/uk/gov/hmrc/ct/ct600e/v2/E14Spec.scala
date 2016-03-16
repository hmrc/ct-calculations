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

class E14Spec extends WordSpec with MockitoSugar with Matchers  {

  val boxRetriever = mock[CT600EBoxRetriever]

  "E14 validation" should {
    "throw validation error when E5 > 0 and E14 < 0" in {
      when(boxRetriever.retrieveE5()).thenReturn(E5(Some(1)))
      E14(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E14"), "error.E14.mustBeZeroOrPositive"))
    }

    "don't throw validation error when E5 > 0 and E14 = 0" in {
      when(boxRetriever.retrieveE5()).thenReturn(E5(Some(1)))
      E14(Some(0)).validate(boxRetriever) shouldBe Set()
    }

    "don't throw validation error when E5 > 0 and E14 > 0" in {
      when(boxRetriever.retrieveE5()).thenReturn(E5(Some(1)))
      E14(Some(10)).validate(boxRetriever) shouldBe Set()
    }

    "don't throw validation error when E5 > 0 and E14 = None" in {
      when(boxRetriever.retrieveE5()).thenReturn(E5(Some(1)))
      E14(None).validate(boxRetriever) shouldBe Set()
    }

    "throw validation error when E5 = 0 and E14 != None" in {
      when(boxRetriever.retrieveE5()).thenReturn(E5(Some(0)))
      E14(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E14"), "error.E14.conditionalMustBeEmpty"))
    }

    "don't throw validation error when E5 = 0 and E14 = None" in {
      when(boxRetriever.retrieveE5()).thenReturn(E5(Some(0)))
      E14(None).validate(boxRetriever) shouldBe Set()
    }
  }

}
