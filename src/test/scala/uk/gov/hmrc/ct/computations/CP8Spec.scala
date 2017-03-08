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

import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.formats._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP8Spec extends WordSpec with Matchers with MockitoSugar {

  implicit val format = Json.format[CP8Holder]

  "CP8 to json" should {
    "create valid json for int value" in {
      val json = Json.toJson(CP8Holder(CP8(Some(1234))))
      json.toString shouldBe """{"cp8":1234}"""
    }
    "create valid json for -ve int" in {
      val json = Json.toJson(CP8Holder(CP8(Some(-1234))))
      json.toString shouldBe """{"cp8":-1234}"""
    }
    "create valid json for None" in {
      val json = Json.toJson(CP8Holder(CP8(None)))
      json.toString shouldBe """{"cp8":null}"""
    }
  }

  "CP8 from json" should {
    "create +ve int from valid json" in {
      val json = Json.parse("""{"cp8":1234}""")
      Json.fromJson[CP8Holder](json).get shouldBe CP8Holder(cp8 = new CP8(Some(1234)))
    }
    "create -ve int from valid json" in {
      val json = Json.parse("""{"cp8":-1234}""")
      Json.fromJson[CP8Holder](json).get shouldBe CP8Holder(cp8 = new CP8(Some(-1234)))
    }
    "create None from valid json" in {
      val json = Json.parse("""{"cp8":null}""")
      Json.fromJson[CP8Holder](json).get shouldBe CP8Holder(cp8 = new CP8(None))
    }
  }

  val boxRetriever = mock[ComputationsBoxRetriever]

  "CP8 validation" should {
    "pass when zero" in {
      CP8(Some(0)).validate(boxRetriever) shouldBe empty
    }
    "pass when at max" in {
      CP8(Some(99999999)).validate(boxRetriever) shouldBe empty
    }
    "pass when at min" in {
      CP8(Some(-99999999)).validate(boxRetriever) shouldBe empty
    }
    "fail when below min" in {
      CP8(Some(-100000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP8"), "error.CP8.below.min", Some(Seq("-99999999", "99999999"))))
    }
    "fail when above max" in {
      CP8(Some(100000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP8"), "error.CP8.above.max", Some(Seq("-99999999", "99999999"))))
    }
    "fail when empty" in {
      CP8(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP8"), "error.CP8.required"))
    }
  }
  
}

case class CP8Holder(cp8: CP8)
