/*
 * Copyright 2023 HM Revenue & Customs
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
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever


class J10ASpec extends AnyWordSpec with MockitoSugar with Matchers {

  "J10A validate" should {
    "not return errors when B140 is false" in {
      val mockBoxRetriever = mock[TaxAvoidanceBoxRetrieverForTest]
      when(mockBoxRetriever.b140()).thenReturn(B140(Some(false)))
      when(mockBoxRetriever.j5()).thenReturn(J5(None))
      when(mockBoxRetriever.j5A()).thenReturn(J5A(None))
      when(mockBoxRetriever.j10()).thenReturn(J10(None))

      J10A(None).validate(mockBoxRetriever) shouldBe Set()
    }

    "return nonBlank errors when B140 is true and J5 and J5A are blank" in {
      val mockBoxRetriever = mock[TaxAvoidanceBoxRetrieverForTest]
      when(mockBoxRetriever.b140()).thenReturn(B140(Some(true)))
      when(mockBoxRetriever.j5()).thenReturn(J5(None))
      when(mockBoxRetriever.j5A()).thenReturn(J5A(None))
      when(mockBoxRetriever.j10()).thenReturn(J10(None))

      J10A(Some(LocalDate.parse("2013-02-01"))).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("J10A"), "error.J10A.nonBlankValue", None))
    }


    "not return errors when B140 is true and J5 and J5A are present and J10 is valid" in {
      val mockBoxRetriever = mock[TaxAvoidanceBoxRetrieverForTest]
      when(mockBoxRetriever.b140()).thenReturn(B140(Some(true)))
      when(mockBoxRetriever.j5()).thenReturn(J5(Some("12345678")))
      when(mockBoxRetriever.j5A()).thenReturn(J5A(Some(LocalDate.parse("2013-02-01"))))
      when(mockBoxRetriever.j10()).thenReturn(J10(None))

      J10A(Some(LocalDate.parse("2013-02-01"))).validate(mockBoxRetriever) shouldBe Set()
    }

    "return required error when B140 is true and J10A is blank" in {
      val mockBoxRetriever = mock[TaxAvoidanceBoxRetrieverForTest]
      when(mockBoxRetriever.b140()).thenReturn(B140(Some(true)))
      when(mockBoxRetriever.j5()).thenReturn(J5(Some("12345678")))
      when(mockBoxRetriever.j5A()).thenReturn(J5A(Some(LocalDate.parse("2013-02-01"))))
      when(mockBoxRetriever.j10()).thenReturn(J10(Some("12345678")))

      J10A(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("J10A"), "error.J10A.required", None))
    }

    "return not after error when B140 is true and J10A is before 18/03/2004" in {
      val mockBoxRetriever = mock[TaxAvoidanceBoxRetrieverForTest]
      when(mockBoxRetriever.b140()).thenReturn(B140(Some(true)))
      when(mockBoxRetriever.j5()).thenReturn(J5(Some("12345678")))
      when(mockBoxRetriever.j5A()).thenReturn(J5A(Some(LocalDate.parse("2013-02-01"))))
      when(mockBoxRetriever.j10()).thenReturn(J10(Some("12345678")))

      J10A(Some(LocalDate.parse("2004-03-17"))).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("J10A"), "error.J10A.not.after", None))
    }
  }
}
