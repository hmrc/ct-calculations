/*
 * Copyright 2023 HM Revenue & Customs
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
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.nir.NorthernIrelandRateValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP286bSpec extends AnyWordSpec with Matchers with MockitoSugar with NorthernIrelandRateValidation{

  "CP286b" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]
    "when empty and Northern Ireland journey is active" when {
      val CP286b = new CP286b(None){
        override val boxId = "CP286b"
        override def mayHaveNirLosses (boxRetriever:ComputationsBoxRetriever): Boolean = true
      }

      "pass validation when CPQ18 is empty" in {

        when(boxRetriever.cpQ18()).thenReturn(CPQ18(None))
        when(boxRetriever.cp286()).thenReturn(CP286(None))
        when(boxRetriever.cp286a()).thenReturn(CP286a(None))
        CP286b.validate(boxRetriever) shouldBe empty
      }

      "pass validation when CPQ18 is false" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(false)))
        when(boxRetriever.cp286()).thenReturn(CP286(None))
        when(boxRetriever.cp286a()).thenReturn(CP286a(None))
        CP286b.validate(boxRetriever) shouldBe empty
      }

      "fail validation when CPQ18 is true" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        when(boxRetriever.cp286()).thenReturn(CP286(None))
        when(boxRetriever.cp286a()).thenReturn(CP286a(None))
        CP286b.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP286b"), "error.CP286b.required"))
      }
    }

    "when has value" when {
      "pass validation when cpQ18 is true and value is < limit" in {
        when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        when(boxRetriever.cp286()).thenReturn(CP286(Some(1000)))
        when(boxRetriever.cp286a()).thenReturn(CP286a(Some(500)))

        val CP286b = new CP286b(Some(499))

        CP286b.validate(boxRetriever) shouldBe empty
      }
    }

    "pass validation when CPQ18 is true and value == limit" in {
      when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
      when(boxRetriever.cp286()).thenReturn(CP286(Some(1000)))
      when(boxRetriever.cp286a()).thenReturn(CP286a(Some(500)))
      val CP286b = new CP286b(Some(500))

      CP286b.validate(boxRetriever) shouldBe empty
    }

    "pass validation when CPQ18 is true, cp286 is 1000, cp286b is 500 and value == 0" in {
      when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
      when(boxRetriever.cp286()).thenReturn(CP286(Some(1000)))
      when(boxRetriever.cp286a()).thenReturn(CP286a(Some(500)))
      val CP286b = new CP286b(Some(0))

      CP286b.validate(boxRetriever) shouldBe empty
    }

    "fail validation when CPQ18 is true, cp286 is 1000, cp286b is 0 and value > cp286" in {
      when(boxRetriever.cpQ18()).thenReturn(CPQ18(Some(true)))
      when(boxRetriever.cp286()).thenReturn(CP286(Some(1000)))
      when(boxRetriever.cp286a()).thenReturn(CP286a(Some(0)))
      val CP286b = new CP286b(Some(1001))

      CP286b.validate(boxRetriever) shouldBe Set(CtValidation(Some("CP286b"),"error.CP286b.outOfRange",Some(List("0", "1,000"))))
    }

  }

}
