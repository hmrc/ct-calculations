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

package uk.gov.hmrc.ct.ct600j.v3

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever


class J10Spec extends WordSpec with MockitoSugar with Matchers {

  "J10 validate" should {
    "not return errors when B140 is false" in {
      val mockBoxRetriever = mock[TaxAvoidanceBoxRetrieverForTest]
      when(mockBoxRetriever.b140()).thenReturn(B140(Some(false)))
      when(mockBoxRetriever.j5()).thenReturn(J5(None))
      when(mockBoxRetriever.j5A()).thenReturn(J5A(None))
      when(mockBoxRetriever.j10A()).thenReturn(J10A(None))

      J10(None).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when B140 is true and J5 and J5A are present and J10A is valid" in {
      val mockBoxRetriever = mock[TaxAvoidanceBoxRetrieverForTest]
      when(mockBoxRetriever.b140()).thenReturn(B140(Some(true)))
      when(mockBoxRetriever.j5()).thenReturn(J5(Some("12345678")))
      when(mockBoxRetriever.j5A()).thenReturn(J5A(Some(LocalDate.parse("2013-02-01"))))
      when(mockBoxRetriever.j10A()).thenReturn(J10A(None))

      J10(Some("12345678")).validate(mockBoxRetriever) shouldBe Set()
    }

    "return required error when B140 is true and J10A is blank" in {
      val mockBoxRetriever = mock[TaxAvoidanceBoxRetrieverForTest]
      when(mockBoxRetriever.b140()).thenReturn(B140(Some(true)))
      when(mockBoxRetriever.j5()).thenReturn(J5(Some("12345678")))
      when(mockBoxRetriever.j5A()).thenReturn(J5A(Some(LocalDate.parse("2013-02-01"))))
      when(mockBoxRetriever.j10A()).thenReturn(J10A(Some(LocalDate.parse("2013-02-01"))))

      J10(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("J10"), "error.J10.required", None))
    }

    "return regex error when B140 is true and J10A is invalid" in {
      val mockBoxRetriever = mock[TaxAvoidanceBoxRetrieverForTest]
      when(mockBoxRetriever.b140()).thenReturn(B140(Some(true)))
      when(mockBoxRetriever.j5()).thenReturn(J5(Some("12345678")))
      when(mockBoxRetriever.j5A()).thenReturn(J5A(Some(LocalDate.parse("2013-02-01"))))
      when(mockBoxRetriever.j10A()).thenReturn(J10A(Some(LocalDate.parse("2013-02-01"))))

      J10(Some("xyz")).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("J10"), "error.J10.regexFailure", None))
    }
  }
}
