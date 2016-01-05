/*
 * Copyright 2016 HM Revenue & Customs
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

class CPQ8Spec extends WordSpec with Matchers {

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

}

case class CPQ8Holder(cpq8: CPQ8)
