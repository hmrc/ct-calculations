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

import org.mockito.Mockito._
import org.mockito.Matchers._
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json
import uk.gov.hmrc.ct.CATO13
import uk.gov.hmrc.ct.box.{CtValidation, ValidatableBox}
import uk.gov.hmrc.ct.computations.Validators.{DonationsValidation, DonationsValidationFixture}
import uk.gov.hmrc.ct.computations.formats._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CPQ21Spec extends AnyWordSpec with Matchers with MockitoSugar with DonationsValidationFixture {

  implicit val format = Json.format[CPQ21Holder]

  "CPQ21 to json" should {
    "create valid json for true value" in {
      val json = Json.toJson(CPQ21Holder(CPQ21(Some(true))))
      json.toString shouldBe """{"cpq21":true}"""
    }
    "create valid json for false value" in {
      val json = Json.toJson(CPQ21Holder(CPQ21(Some(false))))
      json.toString shouldBe """{"cpq21":false}"""
    }
  }

  "CPQ21 from json" should {
    "create true from valid json" in {
      val json = Json.parse("""{"cpq21":true}""")
      Json.fromJson[CPQ21Holder](json).get shouldBe CPQ21Holder(cpq21 = CPQ21(Some(true)))
    }
    "create false from valid json" in {
      val json = Json.parse("""{"cpq21":false}""")
      Json.fromJson[CPQ21Holder](json).get shouldBe CPQ21Holder(cpq21 = CPQ21(Some(false)))
    }
  }

  "CPQ21" when {
    val boxRetriever = mock[ComputationsBoxRetriever]
    when(boxRetriever.cp29()).thenReturn(CP29(10))
    when(boxRetriever.cp999()).thenReturn(CP999(1))
    when(boxRetriever.cp303()).thenReturn(CP303(0))
    when(boxRetriever.cp3030()).thenReturn(CP3030(0))

    "is undefined" should {
      "not validate the box" in {
        CPQ21(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CPQ21"), "error.CPQ21.required"))
      }
    }

    "is false" should {
      "allow empty charitable donations boxes" in {
        when(boxRetriever.cp301()).thenReturn(CP301(None))
        when(boxRetriever.cp302()).thenReturn(CP302(None))
        when(boxRetriever.cp303()).thenReturn(CP303(None))

        CPQ21(Some(false)).validate(boxRetriever) shouldBe empty
      }

      "allow zero values in charitable donations boxes" in {
        when(boxRetriever.cp301()).thenReturn(CP301(0))
        when(boxRetriever.cp302()).thenReturn(CP302(0))
        when(boxRetriever.cp303()).thenReturn(CP303(0))

        CPQ21(Some(false)).validate(boxRetriever) shouldBe empty
      }
    }

    "is true" should {
      when(boxRetriever.cato13()).thenReturn(CATO13(20))

      "not validate if charitable donations boxes are empty" in {
        when(boxRetriever.cp301()).thenReturn(CP301(None))
        when(boxRetriever.cp302()).thenReturn(CP302(None))
        when(boxRetriever.cp303()).thenReturn(CP303(None))

        CPQ21(Some(true)).validate(boxRetriever) shouldBe Set(CtValidation(None, "error.CPQ21.no.charitable.donations"))
      }

      "validate if total charitable donations is equal to net profit chargeable to CT without chartiable donations" in {
        when(boxRetriever.cato13()).thenReturn(CATO13(2))
        when(boxRetriever.cp301()).thenReturn(CP301(1))
        when(boxRetriever.cp302()).thenReturn(CP302(1))

        CPQ21(Some(true)).validate(boxRetriever) shouldBe empty
      }

      "validate when at least one of the charitable donations boxes has a positive value" in {
        when(boxRetriever.cp301()).thenReturn(CP301(1))
        when(boxRetriever.cp302()).thenReturn(CP302(None))
        when(boxRetriever.cp303()).thenReturn(CP303(None))

        CPQ21(Some(true)).validate(boxRetriever) shouldBe empty

        when(boxRetriever.cp301()).thenReturn(CP301(None))
        when(boxRetriever.cp302()).thenReturn(CP302(1))
        when(boxRetriever.cp303()).thenReturn(CP303(None))

        CPQ21(Some(true)).validate(boxRetriever) shouldBe empty

        when(boxRetriever.cp301()).thenReturn(CP301(None))
        when(boxRetriever.cp302()).thenReturn(CP302(None))
        when(boxRetriever.cp303()).thenReturn(CP303(1))

        CPQ21(Some(true)).validate(boxRetriever) shouldBe empty
      }
    }
  }

  testGlobalDonationsValidationErrors(CPQ21(Some(true))) {
    mock[ComputationsBoxRetriever]
  }
}

case class CPQ21Holder(cpq21: CPQ21)
