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

class CPQ19Spec extends WordSpec with Matchers with MockitoSugar {

  "CPQ19" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]

    "when empty" when {
      when(boxRetriever.cpQ17()).thenReturn(CPQ17(None))
      "pass validation when CP118 is zero" in {
        when(boxRetriever.cp118()).thenReturn(CP118(0))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        CPQ19(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CATO01 is zero" in {
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        CPQ19(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when both CATO01 and CP118 are greater than zero" in {
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        CPQ19(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.required"))
      }
      "pass validation when both CATO01 and CP118 are zero" in {
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        when(boxRetriever.cp118()).thenReturn(CP118(0))
        CPQ19(None).validate(boxRetriever) shouldBe empty
      }
    }
    "when true" when {
      when(boxRetriever.cpQ17()).thenReturn(CPQ17(None))
      "fail validation when CP118 is zero" in {
        when(boxRetriever.cp118()).thenReturn(CP118(0))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        CPQ19(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist"))
      }
      "fail validation when CATO01 is zero" in {
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        CPQ19(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist"))
      }
      "fail validation when both CATO01 and CP118 are zero" in {
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        when(boxRetriever.cp118()).thenReturn(CP118(0))
        CPQ19(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist"))
      }
      "pass validation when both CATO01 and CP118 are greater than zero" in {
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        CPQ19(Some(true)).validate(boxRetriever) shouldBe empty
      }
    }
    "when false" when {
      when(boxRetriever.cpQ17()).thenReturn(CPQ17(None))
      "fail validation when CP118 is zero" in {
        when(boxRetriever.cp118()).thenReturn(CP118(0))
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        CPQ19(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist"))
      }
      "fail validation when CATO01 is zero" in {
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        CPQ19(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist"))
      }
      "fail validation when both CATO01 and CP118 are zero" in {
        when(boxRetriever.cato01()).thenReturn(CATO01(0))
        when(boxRetriever.cp118()).thenReturn(CP118(0))
        CPQ19(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist"))
      }
      "pass validation when both CATO01 and CP118 are greater than zero" in {
        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        CPQ19(Some(false)).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ17 is also answered" in {

        when(boxRetriever.cpQ17()).thenReturn(CPQ17(Some(false)))

        when(boxRetriever.cato01()).thenReturn(CATO01(10))
        when(boxRetriever.cp118()).thenReturn(CP118(10))
        CPQ19(Some(false)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ19"), "error.CPQ19.cannot.exist.cpq17"))
      }
    }
  }
}
