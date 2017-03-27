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
import uk.gov.hmrc.ct.accounts.frs10x.retriever.Frs10xDormancyBoxRetriever
import uk.gov.hmrc.ct.box.CtValidation

class AC8089Spec extends WordSpec with Matchers with MockitoSugar with BeforeAndAfterEach {

  val mockBoxRetriever = mock[Frs10xDormancyBoxRetriever]

  "AC8089 should" should {

    "validate fails if dormant and not set" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))
      AC8089(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("AC8089"), "error.AC8089.required", None))
    }

    "validate passes if dormant and set" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))
      AC8089(Some(true)).validate(mockBoxRetriever) shouldBe Set.empty
    }

    "validate passes if not dormant" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(None))
      AC8089(None).validate(mockBoxRetriever) shouldBe Set.empty

      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(None))
      AC8089(Some(true)).validate(mockBoxRetriever) shouldBe Set.empty

      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(Some(false)))
      AC8089(Some(true)).validate(mockBoxRetriever) shouldBe Set.empty
    }
  }
}
