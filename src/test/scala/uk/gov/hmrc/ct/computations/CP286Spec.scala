/*
 * Copyright 2019 HM Revenue & Customs
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
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP286Spec extends WordSpec with Matchers with MockitoSugar {

  "CP286" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]
    "when empty" when {
      val box = new CP286(None) {
        override val boxId = "CP286"
        override def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp283: CP283, cp997: CP997Abstract, cp998: CP998) = 90
      }

      "pass validation when CPQ18 is empty" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(None))
        when(boxRetriever.cp286b()).thenReturn(CP286b(None))
        when(boxRetriever.cp286a()).thenReturn(CP286a(None))
        box.validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ18 is false" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(false)))
        box.validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ18 is true" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        box.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP286"), "error.CP286.required"))
      }
    }

    "when has value" when {
      "pass validation when CPQ18 is true and value < limit" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        val box = new CP286(Some(90)) {
          override val boxId = "CP286"
          override def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp283: CP283, cp997: CP997Abstract, cp998: CP998) = 91
        }
        box.validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ18 is true and value == limit" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        val box = new CP286(Some(90)) {
          override def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp283: CP283, cp997: CP997Abstract, cp998: CP998) = 90
        }
        box.validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ18 is true and value == 0" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        val box = new CP286(Some(0)) {
          override def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp283: CP283, cp997: CP997Abstract, cp998: CP998) = 90
        }
        box.validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ18 is true and value > limit" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        val box = new CP286(Some(90)) {
          override val boxId = "CP286"
          override def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp283: CP283, cp997: CP997Abstract, cp998: CP998) = 89
        }
        box.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP286"), "error.CP286.exceeds.max", Some(Seq("89"))))
      }
      "fail validation when CPQ18 is true and value < 0" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        val box = new CP286(Some(-1)) {
          override val boxId = "CP286"
          override def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp283: CP283, cp997: CP997Abstract, cp998: CP998) = 89
        }
        box.validate(boxRetriever) shouldBe Set(
          CtValidation(Some("CP286"), "error.CP286.below.min", Some(Seq("0"))),
          CtValidation(Some("CP286"), "error.CP286.breakdown.sum.incorrect", None))
      }
      "fail validation when CPQ18 is false and has value" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(false)))
        val box = new CP286(Some(90)) {
          override val boxId = "CP286"
          override def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp283: CP283, cp997: CP997Abstract, cp998: CP998) = 90
        }
        box.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP286"), "error.CP286.cannot.exist"))
      }
      "fail validation when CPQ18 is empty and has value" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(None))
        val box = new CP286(Some(90)) {
          override val boxId = "CP286"
          override def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp283: CP283, cp997: CP997Abstract, cp998: CP998) = 90
        }
        box.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP286"), "error.CP286.cannot.exist"))
      }
      "fail validation when CPQ18 is true, cp286 is 1000, cp286b is 500 and value + cp286b > cp286" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        when(boxRetriever.cp286b()).thenReturn(CP286b(Some(501)))
        when(boxRetriever.cp286a()).thenReturn(CP286a(Some(500)))
        val CP286 = new CP286(Some(1000)) {
          override val boxId = "CP286"
          override def calculateMaximumCP286(cp117: CP117, cato01: CATO01, cp283: CP283, cp997: CP997Abstract, cp998: CP998) = 9999
        }

        CP286.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP286"),"error.CP286.breakdown.sum.incorrect",None))
      }
    }
  }
}
