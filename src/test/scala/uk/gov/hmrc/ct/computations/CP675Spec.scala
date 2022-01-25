/*
 * Copyright 2022 HM Revenue & Customs
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

package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.{Matchers, WordSpec}
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.machineryAndPlant.CP675
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.computations.stubs.StubbedComputationsBoxRetriever

class CP675Spec extends WordSpec with MockitoSugar with Matchers{
  val boxRetriever = mock[ComputationsBoxRetriever]

  "CP675 validation" should {
    "show correct error" in {
      val boxRetriever = new StubbedComputationsBoxRetriever
      val result = CP675(-100).validate(boxRetriever)

    }

    "not show error if value is entered correctly" in {
      val boxRetriever = new StubbedComputationsBoxRetriever
      val result = CP675(100).validate(boxRetriever)

      result shouldBe Set.empty
    }
  }
}
