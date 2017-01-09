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

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.domain.ValidationConstants

class CP281Spec extends WordSpec with Matchers with MockitoSugar {

  "CP281" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]
    "when empty" when {
      "pass validation when CPQ17 is empty" in {
        when(boxRetriever.cpQ17()).thenReturn(CPQ17(None))
        CP281(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ17 is false" in {
        when(boxRetriever.cpQ17()).thenReturn(CPQ17(Some(false)))
        CP281(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ17 is true" in {
        when(boxRetriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CP281(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP281"), "error.CP281.required"))
      }
    }
    "when has value" when {
      "pass validation when CPQ17 is true and value > 0" in {
        when(boxRetriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CP281(Some(1)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ17 is true and value == max" in {
        when(boxRetriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CP281(Some(ValidationConstants.MAX_MONEY_AMOUNT_ALLOWED)).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ17 is true and value > max" in {
        when(boxRetriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CP281(Some(ValidationConstants.MAX_MONEY_AMOUNT_ALLOWED + 1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP281"), "error.CP281.exceeds.max", Some(Seq("99999999"))))
      }
      "fail validation when CPQ17 is true and value == zero" in {
        when(boxRetriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CP281(Some(0)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP281"), "error.CP281.below.min", Some(Seq("1"))))
      }
      "fail validation when CPQ17 is true and value < zero" in {
        when(boxRetriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CP281(Some(-1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP281"), "error.CP281.below.min", Some(Seq("1"))))
      }
      "fail validation when CPQ17 is false and value non empty" in {
        when(boxRetriever.cpQ17()).thenReturn(CPQ17(Some(false)))
        CP281(Some(1)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP281"), "error.CP281.cannot.exist"))
      }
      "fail validation when CPQ17 is empty" in {
        when(boxRetriever.cpQ17()).thenReturn(CPQ17(None))
        CP281(Some(80)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP281"), "error.CP281.cannot.exist"))
      }
    }
  }
}
