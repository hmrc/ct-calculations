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

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json
import uk.gov.hmrc.ct.computations.formats._

class CP7Spec extends WordSpec with Matchers {

  implicit val format = Json.format[CP7Holder]

  "CP7 to json" should {
    "create valid json for int value" in {
      val json = Json.toJson(CP7Holder(CP7(Some(1234))))
      json.toString shouldBe """{"cp7":1234}"""
    }
    "create valid json for None" in {
      val json = Json.toJson(CP7Holder(CP7(None)))
      json.toString shouldBe """{"cp7":null}"""
    }
    "create valid json for default" in {
      val json = Json.toJson(CP7Holder(CP7(None, Some(54321))))
      json.toString shouldBe """{"cp7":54321}"""
    }
  }

  "CP7 from json" should {
    "create +ve int from valid json" in {
      val json = Json.parse("""{"cp7":1234}""")
      Json.fromJson[CP7Holder](json).get shouldBe CP7Holder(cp7 = new CP7(Some(1234), None))
    }
    "create None from valid json" in {
      val json = Json.parse("""{"cp7":null}""")
      Json.fromJson[CP7Holder](json).get shouldBe CP7Holder(cp7 = new CP7(None, None))
    }
  }
}

case class CP7Holder(cp7: CP7)
