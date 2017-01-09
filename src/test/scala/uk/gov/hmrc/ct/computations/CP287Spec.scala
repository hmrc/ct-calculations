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
import org.scalatest.prop.Tables.Table
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP287Spec extends WordSpec with Matchers with MockitoSugar {

  "CP287" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]
    "when empty" when {
      "pass validation when CPQ20 is empty" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(None))
        CP287(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ20 is false" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(Some(false)))
        CP287(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ20 is true" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CP287(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP287"), "error.CP287.required"))
      }
    }
    "when has value" when {
      "pass validation when CPQ20 is true and value < CP118 - CP998" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CP287(Some(80)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ20 is true and value == CP118 - CP998" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CP287(Some(90)).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ20 is true and value > CP118 - CP998" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CP287(Some(91)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP287"), "error.CP287.exceeds.max", Some(List("90"))))
      }
      "fail validation when CPQ20 is false" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(Some(false)))
        CP287(Some(80)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP287"), "error.CP287.cannot.exist"))
      }
      "fail validation when CPQ20 is empty" in {
        when(boxRetriever.cp118()).thenReturn(CP118(100))
        when(boxRetriever.cp998()).thenReturn(CP998(Some(10)))
        when(boxRetriever.cpQ20()).thenReturn(CPQ20(None))
        CP287(Some(80)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP287"), "error.CP287.cannot.exist"))
      }
    }
  }
}
