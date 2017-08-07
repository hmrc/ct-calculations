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

package uk.gov.hmrc.ct.computations

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import org.mockito.Mockito._
import uk.gov.hmrc.ct.box.CtValidation

class CP510Spec extends WordSpec with Matchers with MockitoSugar {

  "CP510" should {
    "pass validation for a value of zero" in {
      val boxRetriever = mock[ComputationsBoxRetriever]
      when(boxRetriever.cp508()).thenReturn(CP508(99999999))
      CP510(Some(0)).validate(boxRetriever) shouldBe empty
    }
    "pass validation for a value equal to CP508" in {
      val boxRetriever = mock[ComputationsBoxRetriever]
      when(boxRetriever.cp508()).thenReturn(CP508(99999999))
      CP510(Some(99999999)).validate(boxRetriever) shouldBe empty
    }
    "fail validation for a value greater than CP508" in {
      val boxRetriever = mock[ComputationsBoxRetriever]
      when(boxRetriever.cp508()).thenReturn(CP508(10))
      CP510(Some(11)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP510"), "error.CP510.above.max", Some(Seq("0", "10"))))
    }
    "fail validation for a value less than zero" in {
      val boxRetriever = mock[ComputationsBoxRetriever]
      when(boxRetriever.cp508()).thenReturn(CP508(0))
      CP510(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP510"), "error.CP510.below.min", Some(Seq("0", "0"))))
    }
  }
}
