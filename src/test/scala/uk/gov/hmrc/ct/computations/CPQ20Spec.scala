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
import uk.gov.hmrc.ct.CATO01
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CPQ20Spec extends WordSpec with Matchers with MockitoSugar {

  "CPQ20" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]

    "when empty" when {
      "pass validation when CPQ19 is false and CP118 == CATO01" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(Some(false)))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        CPQ20(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ19 is true and CP118 == CATO01" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        CPQ20(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ19 is true and CP118 < CATO01" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(1))
        CPQ20(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ19 is true and CP118 > CATO01" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        when(boxRetriever.cp118()).thenReturn(CP118(1))
        CPQ20(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ20"), "error.CPQ20.required"))
      }
      "pass validation when CPQ19 is empty and CP118 == CATO01" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(None))
        when(boxRetriever.cato01()).thenReturn(CATO01(1))
        when(boxRetriever.cp118()).thenReturn(CP118(1))
        CPQ20(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ19 is empty and CP118 > zero and CATO01 > zero but still < cp118" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(None))
        when(boxRetriever.cato01()).thenReturn(CATO01(1))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        CPQ20(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ19 is empty and CP118 > zero and CATO01 == 0" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(None))
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        when(boxRetriever.cp118()).thenReturn(CP118(1))
        CPQ20(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ20"), "error.CPQ20.required"))
      }
    }
    "when true" when {
      "fail validation when CPQ19 is false" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(Some(false)))
        when(boxRetriever.cp118()).thenReturn(CP118(0))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        CPQ20(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ20"), "error.CPQ20.cannot.exist"))
      }
      "pass validation when CPQ19 is empty, CP118 > 0 and CATO01 == zero" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(None))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        CPQ20(Some(true)).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ19 is empty, CP118 == zero and CATO01 == zero" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(None))
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        when(boxRetriever.cp118()).thenReturn(CP118(0))
        CPQ20(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ20"), "error.CPQ20.cannot.exist"))
      }
      "fail validation when CPQ19 is empty, CP118 == zero and CATO01 > zero" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(None))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(0))
        CPQ20(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ20"), "error.CPQ20.cannot.exist"))
      }
      "fail validation when CPQ19 is true, CP118 == CATO01" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        CPQ20(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ20"), "error.CPQ20.cannot.exist"))
      }
      "fail validation when CPQ19 is true, CP118 < CATO01" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(9))
        CPQ20(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ20"), "error.CPQ20.cannot.exist"))
      }
      "pass validation when CPQ19 is true, CP118 > CATO01" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(11))
        CPQ20(Some(true)).validate(boxRetriever) shouldBe empty
      }
    }
    "when false" when {
      "fail validation when CPQ19 is false" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(Some(false)))
        when(boxRetriever.cp118()).thenReturn(CP118(0))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        CPQ20(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ20"), "error.CPQ20.cannot.exist"))
      }
      "pass validation when CPQ19 is empty, CP118 > 0 and CATO01 == zero" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(None))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        CPQ20(Some(false)).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ19 is empty, CP118 == zero and CATO01 == zero" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(None))
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        when(boxRetriever.cp118()).thenReturn(CP118(0))
        CPQ20(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ20"), "error.CPQ20.cannot.exist"))
      }
      "fail validation when CPQ19 is empty, CP118 == zero and CATO01 > zero" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(None))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(0))
        CPQ20(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ20"), "error.CPQ20.cannot.exist"))
      }
      "fail validation when CPQ19 is true, CP118 == CATO01" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        CPQ20(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ20"), "error.CPQ20.cannot.exist"))
      }
      "fail validation when CPQ19 is true, CP118 < CATO01" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(9))
        CPQ20(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ20"), "error.CPQ20.cannot.exist"))
      }
      "pass validation when CPQ19 is true, CP118 > CATO01" in {
        when(boxRetriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(11))
        CPQ20(Some(false)).validate(boxRetriever) shouldBe empty
      }
    }
  }
}
