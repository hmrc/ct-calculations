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

class CPQ21Spec extends WordSpec with Matchers {

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

}

case class CPQ21Holder(cpq21: CPQ21)
