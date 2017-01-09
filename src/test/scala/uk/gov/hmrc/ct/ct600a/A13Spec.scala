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

package uk.gov.hmrc.ct.ct600a

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json
import uk.gov.hmrc.ct.ct600a.v2.formats._
import uk.gov.hmrc.ct.ct600a.v2.A13

class A13Spec extends WordSpec with Matchers {

  implicit val format = Json.format[A13Holder]

  "A13 to json" should {
    "create valid json for int value" in {
      val json = Json.toJson(A13Holder(A13(Some(BigDecimal(1234)))))
      json.toString shouldBe """{"a13":1234}"""
    }
    "create valid json for -ve int" in {
      val json = Json.toJson(A13Holder(A13(Some(BigDecimal(1234.75)))))
      json.toString shouldBe """{"a13":1234.75}"""
    }
    "create valid json for None" in {
      val json = Json.toJson(A13Holder(A13(None)))
      json.toString shouldBe """{"a13":null}"""
    }
  }

  "A13 from json" should {
    "create +ve int from valid json" in {
      val json = Json.parse("""{"a13":1234}""")
      Json.fromJson[A13Holder](json).get shouldBe A13Holder(a13 = A13(Some(BigDecimal(1234))))
    }
    "create decimal from valid json with decimal places" in {
      val json = Json.parse("""{"a13":1234.25}""")
      Json.fromJson[A13Holder](json).get shouldBe A13Holder(a13 = A13(Some(BigDecimal(1234.25))))
    }
    "create None from valid json" in {
      val json = Json.parse("""{"a13":null}""")
      Json.fromJson[A13Holder](json).get shouldBe A13Holder(a13 = A13(None))
    }
  }
}

case class A13Holder(a13: A13)
