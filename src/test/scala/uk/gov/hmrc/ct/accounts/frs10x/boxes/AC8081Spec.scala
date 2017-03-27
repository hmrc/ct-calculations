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

package uk.gov.hmrc.ct.accounts.frs10x.boxes

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, Matchers, WordSpec}
import uk.gov.hmrc.ct.accounts.frs10x.retriever.{Frs10xAccountsBoxRetriever, Frs10xDirectorsBoxRetriever, Frs10xDormancyBoxRetriever, Frs10xFilingQuestionsBoxRetriever}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.box.retriever.FilingAttributesBoxValueRetriever


class AC8081Spec extends WordSpec with Matchers with MockitoSugar with BeforeAndAfterEach {
  trait MockRetriever extends Frs10xAccountsBoxRetriever with Frs10xDormancyBoxRetriever {
    self: FilingAttributesBoxValueRetriever with Frs10xFilingQuestionsBoxRetriever with Frs10xDirectorsBoxRetriever =>
  }

  val mockBoxRetriever = mock[MockRetriever]

  "AC8081 should" should {

    "validate passes if not dormant and set" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(None))
      AC8081(Some(true)).validate(mockBoxRetriever) shouldBe Set.empty
    }

    "validate fails if not dormant and not set" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(None))
      AC8081(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8081"), "error.AC8081.required", None))
    }

    "validate passes if dormant" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))

      AC8081(None).validate(mockBoxRetriever) shouldBe Set.empty

      AC8081(Some(true)).validate(mockBoxRetriever) shouldBe Set.empty
    }
  }
}
