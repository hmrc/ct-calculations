/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json
import uk.gov.hmrc.ct.computations.formats._

class CP14Spec extends WordSpec with Matchers {

  implicit val format = Json.format[CP14Holder]

  "CP14 to json" should {
    "create valid json for int value" in {
      val json = Json.toJson(CP14Holder(CP14(1234)))
      json.toString shouldBe """{"cp14":1234}"""
    }
    "create valid json for -ve int" in {
      val json = Json.toJson(CP14Holder(CP14(-1234)))
      json.toString shouldBe """{"cp14":-1234}"""
    }
  }

  "CP14 from json" should {
    "create +ve int from valid json" in {
      val json = Json.parse("""{"cp14":1234}""")
      Json.fromJson[CP14Holder](json).get shouldBe CP14Holder(cp14 = new CP14(1234))
    }
    "create -ve int from valid json" in {
      val json = Json.parse("""{"cp14":-1234}""")
      Json.fromJson[CP14Holder](json).get shouldBe CP14Holder(cp14 = new CP14(-1234))
    }
  }
}

case class CP14Holder(cp14: CP14)
