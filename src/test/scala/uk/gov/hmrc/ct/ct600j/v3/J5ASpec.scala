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

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frsse2008.retriever.Frsse2008AccountsBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever
import uk.gov.hmrc.ct.ct600.v3.retriever.CT600BoxRetriever
import org.joda.time.LocalDate
import uk.gov.hmrc.ct.ct600j.v3.retriever.CT600JBoxRetriever


class J5ASpec extends WordSpec with MockitoSugar with Matchers {

  "J5A validate" should {
    "not return errors when B140 is false" in {
      val mockBoxRetriever = mock[TaxAvoidanceBoxRetrieverForTest]
      when(mockBoxRetriever.b140()).thenReturn(B140(Some(false)))

      J5A(None).validate(mockBoxRetriever) shouldBe Set()
    }

    "not return errors when B140 is true and J5A is valid" in {
      val mockBoxRetriever = mock[TaxAvoidanceBoxRetrieverForTest]
      when(mockBoxRetriever.b140()).thenReturn(B140(Some(true)))

      J5A(Some(LocalDate.parse("2014-02-01"))).validate(mockBoxRetriever) shouldBe Set()
    }

    "return required error when B140 is true and J5 is blank" in {
      val mockBoxRetriever = mock[TaxAvoidanceBoxRetrieverForTest]
      when(mockBoxRetriever.b140()).thenReturn(B140(Some(true)))

      J5A(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("J5A"), "error.J5A.required", None))
    }
  }
}
