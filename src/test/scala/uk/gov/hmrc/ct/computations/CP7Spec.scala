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
    "create valid json for -ve int" in {
      val json = Json.toJson(CP7Holder(CP7(Some(-1234))))
      json.toString shouldBe """{"cp7":-1234}"""
    }
    "create valid json for None" in {
      val json = Json.toJson(CP7Holder(CP7(None)))
      json.toString shouldBe """{"cp7":null}"""
    }
  }

  "CP7 from json" should {
    "create +ve int from valid json" in {
      val json = Json.parse("""{"cp7":1234}""")
      Json.fromJson[CP7Holder](json).get shouldBe CP7Holder(cp7 = new CP7(Some(1234)))
    }
    "create -ve int from valid json" in {
      val json = Json.parse("""{"cp7":-1234}""")
      Json.fromJson[CP7Holder](json).get shouldBe CP7Holder(cp7 = new CP7(Some(-1234)))
    }
    "create None from valid json" in {
      val json = Json.parse("""{"cp7":null}""")
      Json.fromJson[CP7Holder](json).get shouldBe CP7Holder(cp7 = new CP7(None))
    }
  }
}

case class CP7Holder(cp7: CP7)