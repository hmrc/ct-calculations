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

class E27Spec extends WordSpec with MockitoSugar with Matchers  {

  val boxRetriever = mock[CT600EBoxRetriever]

  "E4 validation" should {
    "make E27 required when E26 = 2" in {
      when(boxRetriever.e26()).thenReturn(E26(Some(CharityLoansAndInvestments.SomeLoansAndInvestments)))
      E27(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("E27"), "error.E27.required", None))
    }

    "don't validate E27 if E26 != 2" in {
      when(boxRetriever.e26()).thenReturn(E26(Some(CharityLoansAndInvestments.AllLoansAndInvestments)))
      E27(None).validate(boxRetriever) shouldBe Set()
    }

    "return validation error if value is 0" in {
      when(boxRetriever.e26()).thenReturn(E26(Some(CharityLoansAndInvestments.SomeLoansAndInvestments)))
      E27(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E27"), "error.E27.required", None))
    }

    "return validation error if value is -1" in {
      when(boxRetriever.e26()).thenReturn(E26(Some(CharityLoansAndInvestments.SomeLoansAndInvestments)))
      E27(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("E27"), "error.E27.mustBePositive", None))
    }

    "don't return validation error if value is over 0" in {
      when(boxRetriever.e26()).thenReturn(E26(Some(CharityLoansAndInvestments.SomeLoansAndInvestments)))
      E27(Some(1)).validate(boxRetriever) shouldBe Set()
    }
  }
}
