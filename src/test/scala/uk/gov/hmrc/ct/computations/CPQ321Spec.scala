/*
 * Copyright 2022 HM Revenue & Customs
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
import org.mockito.Mockito.when
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json
import uk.gov.hmrc.ct.CATO13
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.Validators.DonationsValidationFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import uk.gov.hmrc.ct.computations.formats._

class CPQ321Spec extends WordSpec with Matchers with MockitoSugar with DonationsValidationFixture {

  implicit val format = Json.format[CPQ321Holder]

  "CPQ321 to json" should {
    "create valid json for true value" in {
      val json = Json.toJson(CPQ321Holder(CPQ321(Some(true))))
      json.toString shouldBe """{"cpq321":true}"""
    }
    "create valid json for false value" in {
      val json = Json.toJson(CPQ321Holder(CPQ321(Some(false))))
      json.toString shouldBe """{"cpq321":false}"""
    }
  }

  "CPQ321 from json" should {
    "create true from valid json" in {
      val json = Json.parse("""{"cpq321":true}""")
      Json.fromJson[CPQ321Holder](json).get shouldBe CPQ321Holder(cpq321 = CPQ321(Some(true)))
    }
    "create false from valid json" in {
      val json = Json.parse("""{"cpq321":false}""")
      Json.fromJson[CPQ321Holder](json).get shouldBe CPQ321Holder(cpq321 = CPQ321(Some(false)))
    }
  }

  "CPQ321 validation if the AP ends after 31/03/2017" should {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2017-04-01")))
    when(retriever.cp29()).thenReturn(CP29(100))
    when(retriever.cp999()).thenReturn(CP999(0))
    when(retriever.cp303()).thenReturn(CP303(0))
    when(retriever.cp3010()).thenReturn(CP3010(0))
    when(retriever.cp3020()).thenReturn(CP3020(0))
    when(retriever.cp3030()).thenReturn(CP3030(0))
    when(retriever.cato13()).thenReturn(CATO13(0))

    "fail if None" in {
      CPQ321(None).validate(retriever) shouldBe Set(CtValidation(Some("CPQ321"), "error.CPQ321.required"))
    }
    "pass if Some(false)" in {
      CPQ321(Some(false)).validate(retriever) shouldBe Set.empty[CtValidation]
    }
    "fail if true but all dependent boxes are 0" in {
      when(retriever.cp3010).thenReturn(CP3010(0))
      when(retriever.cp3020).thenReturn(CP3020(0))
      when(retriever.cp3030).thenReturn(CP3030(0))
      CPQ321(Some(true)).validate(retriever) shouldBe Set(CtValidation(None, "error.CPQ321.no.grassroots.donations"))
    }
    "pass if true and cp3010 is positive" in {
      when(retriever.cp3010).thenReturn(CP3010(1))
      when(retriever.cp3020).thenReturn(CP3020(0))
      when(retriever.cp3030).thenReturn(CP3030(0))
      CPQ321(Some(true)).validate(retriever) shouldBe empty
    }
    "pass if true and cp3020 is positive" in {
      when(retriever.cp3010).thenReturn(CP3010(0))
      when(retriever.cp3020).thenReturn(CP3020(1))
      when(retriever.cp3030).thenReturn(CP3030(0))
      CPQ321(Some(true)).validate(retriever) shouldBe empty
    }
    "pass if true and cp3030 is positive" in {
      when(retriever.cp3010).thenReturn(CP3010(0))
      when(retriever.cp3020).thenReturn(CP3020(0))
      when(retriever.cp3030).thenReturn(CP3030(1))
      CPQ321(Some(true)).validate(retriever) shouldBe empty
    }
  }

  "CPQ321 validation if the AP ends before 01/04/2017" should {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2017-03-31")))
    when(retriever.cp29()).thenReturn(CP29(100))
    when(retriever.cp999()).thenReturn(CP999(0))
    when(retriever.cp303()).thenReturn(CP303(0))
    when(retriever.cp3010()).thenReturn(CP3010(0))
    when(retriever.cp3020()).thenReturn(CP3020(0))
    when(retriever.cp3030()).thenReturn(CP3030(0))
    when(retriever.cato13()).thenReturn(CATO13(0))

    "fail if None" in {
      CPQ321(None).validate(retriever) shouldBe Set.empty
    }
  }

  testGlobalDonationsValidationErrors(CPQ321(Some(true))) {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cp2()).thenReturn(CP2(LocalDate.parse("2017-04-01")))
    when(retriever.cp3010).thenReturn(CP3010(1))
    when(retriever.cp3020).thenReturn(CP3020(0))
    when(retriever.cp3030).thenReturn(CP3030(0))
    retriever
  }
}

case class CPQ321Holder(cpq321: CPQ321)
