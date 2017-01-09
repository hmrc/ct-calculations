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

package uk.gov.hmrc.ct.ct600.v3

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600.v3.retriever.{RepaymentsBoxRetriever, CT600BoxRetriever}


class B860Spec extends WordSpec with MockitoSugar with Matchers {

  "B860 validate" should {
    "not return errors when REPAYMENTSQ1 is true and B860 is empty" in {
      val mockBoxRetriever = mock[RepaymentsBoxRetriever]
      when(mockBoxRetriever.repaymentsQ1()).thenReturn(REPAYMENTSQ1(Some(true)))

      B860(None).validate(mockBoxRetriever) shouldBe Set()
    }

    "return error when REPAYMENTSQ1 is true and B860 is not empty" in {
      val mockBoxRetriever = mock[RepaymentsBoxRetriever]
      when(mockBoxRetriever.repaymentsQ1()).thenReturn(REPAYMENTSQ1(Some(true)))

      B860(Some(100)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("B860"), "error.B860.nonBlankValue"))
    }

    "not return errors when REPAYMENTSQ1 is false and B860 is valid" in {
      val mockBoxRetriever = mock[RepaymentsBoxRetriever]
      when(mockBoxRetriever.repaymentsQ1()).thenReturn(REPAYMENTSQ1(Some(false)))

      B860(Some(100)).validate(mockBoxRetriever) shouldBe Set()
    }

    "return error when REPAYMENTSQ1 is false and B860 is blank" in {
      val mockBoxRetriever = mock[RepaymentsBoxRetriever]
      when(mockBoxRetriever.repaymentsQ1()).thenReturn(REPAYMENTSQ1(Some(false)))

      B860(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("B860"), "error.B860.required", None))
    }

    "return error when REPAYMENTSQ1 is false and B860 is negative" in {
      val mockBoxRetriever = mock[RepaymentsBoxRetriever]
      when(mockBoxRetriever.repaymentsQ1()).thenReturn(REPAYMENTSQ1(Some(false)))

      B860(Some(-1)).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("B860"), "error.B860.mustBeZeroOrPositive", None))
    }
  }
}
