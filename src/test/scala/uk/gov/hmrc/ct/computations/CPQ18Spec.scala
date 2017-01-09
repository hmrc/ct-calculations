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

class CPQ18Spec extends WordSpec with Matchers with MockitoSugar {

  "CPQ18" should {

    "when empty" when {
      "fail validation when CPQ17 is false and CPQ19 is not defined" in new SetUpboxRetriever {

        when(retriever.cpQ17()).thenReturn(CPQ17(Some(false)))
        CPQ18(None).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.required"))
      }

      "pass validation when CPQ17 is true and CPQ19 is not defined" in new SetUpboxRetriever {

        when(retriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CPQ18(None).validate(retriever) shouldBe empty
      }

      "fail validation when CPQ19 is false and CPQ17 is not defined" in new SetUpboxRetriever {

        when(retriever.cpQ19()).thenReturn(CPQ19(Some(false)))
        CPQ18(None).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.required"))
      }

      "pass validation when CPQ19 is true and CPQ17 is not defined" in new SetUpboxRetriever {

        when(retriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        CPQ18(None).validate(retriever) shouldBe empty
      }

      "pass validation when CPQ17 is not defined and CPQ19 is not defined" in new SetUpboxRetriever {

        CPQ18(None).validate(retriever) shouldBe empty
      }

      "pass validation when CPQ20 is true and CP118 == 0" in new SetUpboxRetriever {

        when(retriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CPQ18(None).validate(retriever) shouldBe empty
      }

      "fail validation when CP117 and CP118 == zero and CATO01 > 0" in new SetUpboxRetriever {

        when(retriever.cato01()).thenReturn(CATO01(1))
        CPQ18(None).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.required"))
      }

      "pass validation  when TP no NTP CP284 == CP117, CPQ17 = true" in new SetUpboxRetriever {

        when(retriever.cp117()).thenReturn(CP117(1))
        when(retriever.cp284()).thenReturn(CP284(Some(1)))
        when(retriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        when(retriever.cpQ18()).thenReturn(CPQ18(Some(true)))
        CPQ18(None).validate(retriever) shouldBe empty
      }

      "pass validation when TL no NTP with CPQ20 = true" in new SetUpboxRetriever {

        when(retriever.cp118()).thenReturn(CP118(1))
        when(retriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CPQ18(None).validate(retriever) shouldBe empty
      }
    }

    "when true" when {
      "pass validation when CPQ17 is false and CPQ19 is not defined" in new SetUpboxRetriever {

        when(retriever.cpQ17()).thenReturn(CPQ17(Some(false)))
        CPQ18(Some(true)).validate(retriever) shouldBe empty
      }

      "fail validation when CPQ17 is true and CPQ19 is not defined" in new SetUpboxRetriever {

        when(retriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CPQ18(Some(true)).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.cannot.exist"))
      }

      "pass validation when CPQ19 is false and CPQ17 is not defined" in new SetUpboxRetriever {

        when(retriever.cpQ19()).thenReturn(CPQ19(Some(false)))
        CPQ18(Some(true)).validate(retriever) shouldBe empty
      }

      "fail validation when true and CPQ19 is true and CPQ17 is not defined" in new SetUpboxRetriever {

        when(retriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        CPQ18(Some(true)).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.cannot.exist"))
      }

      "fail validation when CPQ17 is not defined and CPQ19 is not defined" in new SetUpboxRetriever {

        CPQ18(Some(true)).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.cannot.exist"))
      }

      "fail validation when true and CPQ20 is true and CP118 == 0" in new SetUpboxRetriever {

        when(retriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CPQ18(Some(true)).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.cannot.exist"))
      }

      "pass validation when CP117 and CP118 == zero and CATO01 > 0" in new SetUpboxRetriever {

        when(retriever.cato01()).thenReturn(CATO01(1))
        CPQ18(Some(true)).validate(retriever) shouldBe empty
      }

      "pass validation when TP no NTP CP284 > CP117, CPQ17 = true" in new SetUpboxRetriever {

        when(retriever.cp117()).thenReturn(CP117(1))
        when(retriever.cp284()).thenReturn(CP284(Some(2)))
        when(retriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CPQ18(Some(true)).validate(retriever) shouldBe empty
      }

      "pass validation when TP with NTP CP284 + CATO01 > 0, CPQ17 = true" in new SetUpboxRetriever {

        when(retriever.cp117()).thenReturn(CP117(1))
        when(retriever.cp284()).thenReturn(CP284(Some(1)))
        when(retriever.cato01()).thenReturn(CATO01(1))
        when(retriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CPQ18(Some(true)).validate(retriever) shouldBe empty
      }

      "fail validation when TP no NTP CP284 == CP117, CPQ17 = true" in new SetUpboxRetriever {

        when(retriever.cp117()).thenReturn(CP117(1))
        when(retriever.cp284()).thenReturn(CP284(Some(1)))
        when(retriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CPQ18(Some(true)).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.cannot.exist"))
      }

      "fail validation when TL no NTP with CPQ20 = true" in new SetUpboxRetriever {

        when(retriever.cp118()).thenReturn(CP118(1))
        when(retriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CPQ18(Some(true)).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.cannot.exist"))
      }

      "pass vaildation when TL with NTP CPQ19 = true, CP118 - CATO01 < 0" in new SetUpboxRetriever {

        when(retriever.cp118()).thenReturn(CP118(1))
        when(retriever.cato01()).thenReturn(CATO01(2))
        when(retriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        CPQ18(Some(true)).validate(retriever) shouldBe empty
      }
    }

    "when false" when {
      "pass validation when CPQ17 is false and CPQ19 is not defined" in new SetUpboxRetriever {

        when(retriever.cpQ17()).thenReturn(CPQ17(Some(false)))
        CPQ18(Some(false)).validate(retriever) shouldBe empty
      }

      "fail validation when CPQ17 is true and CPQ19 is not defined" in new SetUpboxRetriever {

        when(retriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CPQ18(Some(false)).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.cannot.exist"))
      }

      "pass validation when CPQ19 is false and CPQ17 is not defined" in new SetUpboxRetriever {

        when(retriever.cpQ19()).thenReturn(CPQ19(Some(false)))
        CPQ18(Some(false)).validate(retriever) shouldBe empty
      }

      "fail validation when CPQ19 is true and CPQ17 is not defined" in new SetUpboxRetriever {

        when(retriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        CPQ18(Some(false)).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.cannot.exist"))
      }

      "fail validation when CPQ19 is true and CPQ17 is not defined and boxes as per scenario" in new SetUpboxRetriever {

        when(retriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        when(retriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        when(retriever.cp287()).thenReturn(CP287(Some(10000)))
        when(retriever.cp288()).thenReturn(CP288(Some(13404)))
        when(retriever.cato01()).thenReturn(CATO01(1300))
        when(retriever.cp118()).thenReturn(CP118(24704))
        when(retriever.cp117()).thenReturn(CP117(0))
        CPQ18(Some(false)).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.cannot.exist"))
      }

      "fail validation when CPQ17 is not defined and CPQ19 is not defined" in new SetUpboxRetriever {

        CPQ18(Some(false)).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.cannot.exist"))
      }

      "fail validation when CPQ20 is true and CP118 == 0" in new SetUpboxRetriever {

        when(retriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CPQ18(Some(false)).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.cannot.exist"))
      }

      "pass validation when CP117 and CP118 == zero and CATO01 > 0" in new SetUpboxRetriever {

        when(retriever.cato01()).thenReturn(CATO01(1))
        CPQ18(Some(false)).validate(retriever) shouldBe empty
      }

      "pass validation when TP no NTP CP284 > CP117, CPQ17 = true and CPQ18 true" in new SetUpboxRetriever {

        when(retriever.cp117()).thenReturn(CP117(1))
        when(retriever.cp284()).thenReturn(CP284(Some(2)))
        when(retriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CPQ18(Some(false)).validate(retriever) shouldBe empty
      }

      "pass validation when TP with NTP CP284 + CATO01 > 0, CPQ17 = true and CPQ18 true" in new SetUpboxRetriever {

        when(retriever.cp117()).thenReturn(CP117(1))
        when(retriever.cp284()).thenReturn(CP284(Some(1)))
        when(retriever.cato01()).thenReturn(CATO01(1))
        when(retriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CPQ18(Some(false)).validate(retriever) shouldBe empty
      }

      "fail validation when TP no NTP CP284 == CP117, CPQ17 = true" in new SetUpboxRetriever {

        when(retriever.cp117()).thenReturn(CP117(1))
        when(retriever.cp284()).thenReturn(CP284(Some(1)))
        when(retriever.cpQ17()).thenReturn(CPQ17(Some(true)))
        CPQ18(Some(false)).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.cannot.exist"))
      }

      "fail validation when TL no NTP with CPQ20 = true" in new SetUpboxRetriever {

        when(retriever.cp118()).thenReturn(CP118(1))
        when(retriever.cpQ20()).thenReturn(CPQ20(Some(true)))
        CPQ18(Some(false)).validate(retriever) shouldBe Set(CtValidation(Some("CPQ18"), "error.CPQ18.cannot.exist"))
      }

      "pass vaildation when TL with NTP CPQ19 = true, CP118 - CATO01 < 0, CPQ18 = true" in new SetUpboxRetriever {

        when(retriever.cp118()).thenReturn(CP118(1))
        when(retriever.cato01()).thenReturn(CATO01(2))
        when(retriever.cpQ19()).thenReturn(CPQ19(Some(true)))
        CPQ18(Some(false)).validate(retriever) shouldBe empty
      }
    }
  }
}

trait SetUpboxRetriever extends MockitoSugar {

  val retriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]

  def setUpRetriever() = {

    when(retriever.cpQ17()).thenReturn(CPQ17(None))

    when(retriever.cpQ18()).thenReturn(CPQ18(None))

    when(retriever.cpQ19()).thenReturn(CPQ19(None))

    when(retriever.cpQ20()).thenReturn(CPQ20(None))

    when(retriever.cp117()).thenReturn(CP117(0))

    when(retriever.cp118()).thenReturn(CP118(0))

    when(retriever.cp284()).thenReturn(CP284(None))

    when(retriever.cato01()).thenReturn(CATO01(0))
  }

  setUpRetriever()
}
