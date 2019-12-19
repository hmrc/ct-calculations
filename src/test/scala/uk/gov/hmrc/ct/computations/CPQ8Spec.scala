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
import play.api.libs.json.Json
import uk.gov.hmrc.ct.computations.formats._
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CPQ8Spec extends WordSpec with Matchers with MockitoSugar {

  implicit val format = Json.format[CPQ8Holder]

  "CPQ8 to json" should {
    "create valid json for true value" in {
      val json = Json.toJson(CPQ8Holder(CPQ8(Some(true))))
      json.toString shouldBe """{"cpq8":true}"""
    }
    "create valid json for false value" in {
      val json = Json.toJson(CPQ8Holder(CPQ8(Some(false))))
      json.toString shouldBe """{"cpq8":false}"""
    }
  }

  "CPQ8 from json" should {
    "create true from valid json" in {
      val json = Json.parse("""{"cpq8":true}""")
      Json.fromJson[CPQ8Holder](json).get shouldBe CPQ8Holder(cpq8 = CPQ8(Some(true)))
    }
    "create false from valid json" in {
      val json = Json.parse("""{"cpq8":false}""")
      Json.fromJson[CPQ8Holder](json).get shouldBe CPQ8Holder(cpq8 = CPQ8(Some(false)))
    }
  }

  "CPQ8" should {
    val boxRetriever: ComputationsBoxRetriever = mock[ComputationsBoxRetriever]

    val beforeSBA = LocalDate.parse("2018-10-29")

    "when empty" when {
      "pass validation when CPQ7 is false" in {
        when(boxRetriever.cp2()).thenReturn(CP2(beforeSBA))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))
        CPQ8(None).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ7 is empty" in {
        when(boxRetriever.cp2()).thenReturn(CP2(beforeSBA))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(None))
        CPQ8(None).validate(boxRetriever) shouldBe empty
      }
      "fail validation when CPQ7 is true" in {
        when(boxRetriever.cp2()).thenReturn(CP2(beforeSBA))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))
        CPQ8(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ8"), "error.CPQ8.required"))
      }
    }
    "when false" when {
      "pass validation when CPQ7 is false" in {
        when(boxRetriever.cp2()).thenReturn(CP2(beforeSBA))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))
        CPQ8(Some(false)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ7 is empty" in {
        when(boxRetriever.cp2()).thenReturn(CP2(beforeSBA))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(None))
        CPQ8(Some(false)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ7 is true" in {
        when(boxRetriever.cp2()).thenReturn(CP2(beforeSBA))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))
        CPQ8(Some(false)).validate(boxRetriever) shouldBe empty
      }
    }
    "when true" when {
      "fail validation when CPQ7 is false" in {
        when(boxRetriever.cp2()).thenReturn(CP2(beforeSBA))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))
        CPQ8(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ8"), "error.CPQ8.notClaiming.required"))
      }
      "pass validation when CPQ7 is empty" in {
        when(boxRetriever.cp2()).thenReturn(CP2(beforeSBA))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(None))
        CPQ8(Some(true)).validate(boxRetriever) shouldBe empty
      }
      "pass validation when CPQ7 is true" in {
        when(boxRetriever.cp2()).thenReturn(CP2(beforeSBA))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))
        CPQ8(Some(true)).validate(boxRetriever) shouldBe empty
      }
    }

  }

}

case class CPQ8Holder(cpq8: CPQ8)