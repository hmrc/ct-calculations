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

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mock.MockitoSugar
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class LEC01Spec extends WordSpec with MockitoSugar with Matchers {

  "LEC01" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]

    "when empty" when {
      "pass validation when CPQ1000 is false" in {
        when(boxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(false)))
        LEC01().validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ1000 is empty" in {
        when(boxRetriever.cpQ1000()).thenReturn(CPQ1000(None))
        LEC01().validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ1000 is true" in {
        when(boxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(true)))
        LEC01().validate(boxRetriever) shouldBe Set(CtValidation(Some("LEC01"), "error.LEC01.required"))
      }
    }
    "when cars exist" when {
      val cars = List(Car("LG64 RDO", true, 26000, 12, new LocalDate("2015-04-01")))
      "fail validation when CPQ1000 is false" in {
        when(boxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(false)))
        LEC01(cars).validate(boxRetriever) shouldBe Set(CtValidation(Some("LEC01"), "error.LEC01.cannot.exist"))
      }

      "fail validation when CPQ1000 is empty" in {
        when(boxRetriever.cpQ1000()).thenReturn(CPQ1000(None))
        LEC01(cars).validate(boxRetriever) shouldBe Set(CtValidation(Some("LEC01"), "error.LEC01.cannot.exist"))
      }

      "pass validation when CPQ1000 is true" in {
        when(boxRetriever.cpQ1000()).thenReturn(CPQ1000(Some(true)))
        LEC01(cars).validate(boxRetriever) shouldBe empty
      }
    }
  }
}
