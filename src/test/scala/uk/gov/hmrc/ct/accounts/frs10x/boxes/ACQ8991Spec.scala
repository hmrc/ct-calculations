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

class ACQ8991Spec extends WordSpec with Matchers with MockitoSugar with BeforeAndAfterEach {

  val mockBoxRetriever = mock[Frs10xDormancyBoxRetriever]

  "ACQ8991 should" should {

    "validate successfully when not set and ACQ8999 is unset or false" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(None))
      ACQ8991(None).validate(mockBoxRetriever) shouldBe Set.empty

      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(Some(false)))
      ACQ8991(None).validate(mockBoxRetriever) shouldBe Set.empty
    }

    "validate successfully when set and ACQ8999 is true" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))
      ACQ8991(Some(true)).validate(mockBoxRetriever) shouldBe Set.empty
      ACQ8991(Some(false)).validate(mockBoxRetriever) shouldBe Set.empty
    }

    "fail validation when not set and ACQ8999 is true" in {
      when(mockBoxRetriever.acq8999()).thenReturn(ACQ8999(Some(true)))
      ACQ8991(None).validate(mockBoxRetriever) shouldBe Set(CtValidation(Some("ACQ8991"), "error.ACQ8991.required", None))
    }
  }
}
