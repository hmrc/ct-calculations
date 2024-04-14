/*
 * Copyright 2024 HM Revenue & Customs
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
import play.api.libs.json.Json
import uk.gov.hmrc.ct.computations.formats._
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.utils.UnitSpec

class CPQ8Spec extends UnitSpec {

  implicit val format = Json.format[CPQ8Holder]

  private val boxId = "CPQ8"

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
    val afterSBA = LocalDate.parse("2018-10-30")

    "when empty" when {
      "pass validation when CPQ7, CPQ10, CPQ11 are false" in {
        when(boxRetriever.cp2()).thenReturn(CP2(afterSBA))
        when(boxRetriever.cpQ7()) thenReturn CPQ7(Some(false))
        when(boxRetriever.cpQ10()) thenReturn CPQ10(Some(false))
        when(boxRetriever.cpQ11()) thenReturn CPQ11(Some(false))

        CPQ8(None).validate(boxRetriever) shouldBe validationSuccess
      }

      "fail validation when CPQ7, CPQ10 or CPQ11 is true" in {
        when(boxRetriever.cp2()) thenReturn CP2(afterSBA)
        when(boxRetriever.cpQ7()) thenReturn CPQ7(Some(true))
        when(boxRetriever.cpQ10()) thenReturn CPQ10(Some(true))
        when(boxRetriever.cpQ11()) thenReturn CPQ11(Some(true))

        CPQ8(None).validate(boxRetriever) shouldBe Set(CtValidation(Some(boxId), "error.CPQ8.required"))
      }
    }
    "when false" when {
      "pass validation when CPQ7 is false" in {
        when(boxRetriever.cp2()).thenReturn(CP2(beforeSBA))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(false)))

        CPQ8(Some(false)).validate(boxRetriever) shouldBe validationSuccess
      }
      "pass validation when CPQ7 is empty" in {
        when(boxRetriever.cp2()).thenReturn(CP2(beforeSBA))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(None))

        CPQ8(Some(false)).validate(boxRetriever) shouldBe validationSuccess
      }
      "pass validation when CPQ7 is true" in {
        when(boxRetriever.cp2()).thenReturn(CP2(beforeSBA))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))

        CPQ8(Some(false)).validate(boxRetriever) shouldBe validationSuccess
      }
    }
    "when true, pass validation" when {
      "CPQ7 is true" in {
        when(boxRetriever.cp2()).thenReturn(CP2(afterSBA))
        when(boxRetriever.cpQ7()).thenReturn(CPQ7(Some(true)))

        CPQ8(Some(true)).validate(boxRetriever) shouldBe validationSuccess
      }

      "CPQ10 is true" in {
        when(boxRetriever.cp2()).thenReturn(CP2(afterSBA))
        when(boxRetriever.cpQ10()).thenReturn(CPQ10(Some(true)))

        CPQ8(Some(true)).validate(boxRetriever) shouldBe validationSuccess
      }

      "CPQ11 is true" in {
        when(boxRetriever.cp2()).thenReturn(CP2(afterSBA))
        when(boxRetriever.cpQ11()).thenReturn(CPQ11(Some(true)))

        CPQ8(Some(true)).validate(boxRetriever) shouldBe validationSuccess
      }

      "CPQ7, CPQ10 or CPQ11 are true" in {
        when(boxRetriever.cp2()).thenReturn(CP2(afterSBA))
        when(boxRetriever.cpQ7()) thenReturn CPQ7(Some(true))
        when(boxRetriever.cpQ10()) thenReturn CPQ10(Some(true))
        when(boxRetriever.cpQ11()) thenReturn CPQ11(Some(true))

        CPQ8(Some(true)).validate(boxRetriever) shouldBe validationSuccess

      }
    }

  }
}

case class CPQ8Holder(cpq8: CPQ8)