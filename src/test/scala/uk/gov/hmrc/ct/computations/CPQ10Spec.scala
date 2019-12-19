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

import org.joda.time.LocalDate
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CPQ10Spec extends WordSpec with Matchers with MockitoSugar {



  val beforeSBAActive = LocalDate.parse("2019-03-15")
  val afterSBAActive = LocalDate.parse("2019-04-02")

  val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]

  "CPQ10" should {
    "when empty" when {
      "pass validation when CPQ7 is false" in {
        when(boxRetriever.cp1()).thenReturn(CP1(beforeSBAActive))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))
        CPQ10(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ7 is empty" in {
        when(boxRetriever.cp1()).thenReturn(CP1(beforeSBAActive))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(None))
        CPQ10(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ7 is true" in {
        when(boxRetriever.cp1()).thenReturn(CP1(beforeSBAActive))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))
        CPQ10(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ10"), "error.CPQ10.required"))
      }
    }
    "when false" when {
      "pass validation when CPQ7 is false" in {
        when(boxRetriever.cp1()).thenReturn(CP1(beforeSBAActive))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))
        CPQ10(Some(false)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ7 is empty" in {
        when(boxRetriever.cp1()).thenReturn(CP1(beforeSBAActive))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(None))
        CPQ10(Some(false)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ7 is true" in {
        when(boxRetriever.cp1()).thenReturn(CP1(beforeSBAActive))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))
        CPQ10(Some(false)).validate(boxRetriever) shouldBe empty
      }
    }
    "when true" when {
      "fail validation when CPQ7 is false" in {
        when(boxRetriever.cp1()).thenReturn(CP1(beforeSBAActive))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))
        CPQ10(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ10"), "error.CPQ10.notClaiming.required"))
      }
      "pass validation when CPQ7 is empty" in {
        when(boxRetriever.cp1()).thenReturn(CP1(beforeSBAActive))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(None))
        CPQ10(Some(true)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ7 is true" in {
        when(boxRetriever.cp1()).thenReturn(CP1(beforeSBAActive))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))
        CPQ10(Some(true)).validate(boxRetriever) shouldBe empty
      }

      "pass validation after SBA is live" in {
        when(boxRetriever.cp1()).thenReturn(CP1(afterSBAActive))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))
        CPQ10(Some(true)).validate(boxRetriever) shouldBe empty
      }
    }
  }
}