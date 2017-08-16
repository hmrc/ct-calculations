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

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP510Spec extends WordSpec with Matchers with MockitoSugar {

  "CP510" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]
    "when empty" when {
      "pass validation when CP508 is empty" in {
        when(boxRetriever.cp508()).thenReturn(CP508(CP503(None)))
        CP510(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CP508 is not empty" in {
        when(boxRetriever.cp508()).thenReturn(CP508(10))
        CP510(None).validate(boxRetriever) shouldBe empty
      }
    }
    "when non empty" when {
      "fail validation when smaller than zero" in {
        when(boxRetriever.cp508()).thenReturn(CP508(CP503(Some(5))))
        CP510(Some(-5)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP510"), s"error.CP510.mustBeZeroOrPositive"))
      }
      "fail validation when greater than CP508" in {
        when(boxRetriever.cp508()).thenReturn(CP508(CP503(Some(5))))
        CP510(Some(10)).validate(boxRetriever) shouldBe Set(CtValidation(None, "block.incomeFromProperty.unAllowable.error"))
      }
      "fail validation when greater than CP508 (because CP503 is none, and replaced by zero CP508 in implementation)" in {
        when(boxRetriever.cp508()).thenReturn(CP508(CP503(None)))
        CP510(Some(5)).validate(boxRetriever) shouldBe Set(CtValidation(None, "block.incomeFromProperty.unAllowable.error"))
      }
      "pass validation when smaller than CP508" in {
        when(boxRetriever.cp508()).thenReturn(CP508(CP503(Some(10))))
        CP510(Some(5)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when equal to CP508" in {
        when(boxRetriever.cp508()).thenReturn(CP508(CP503(Some(10))))
        CP510(Some(10)).validate(boxRetriever) shouldBe empty
      }
    }
  }
}