package uk.gov.hmrc.ct.computations

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json
import uk.gov.hmrc.ct.computations.formats._

class CPQ21Spec extends WordSpec with Matchers {

  implicit val format = Json.format[CPQ21Holder]

  "CPQ21 to json" should {
    "create valid json for true value" in {
      val json = Json.toJson(CPQ21Holder(CPQ21(true)))
      json.toString shouldBe """{"cpq21":true}"""
    }
    "create valid json for false value" in {
      val json = Json.toJson(CPQ21Holder(CPQ21(false)))
      json.toString shouldBe """{"cpq21":false}"""
    }
  }

  "CPQ21 from json" should {
    "create true from valid json" in {
      val json = Json.parse("""{"cpq21":true}""")
      Json.fromJson[CPQ21Holder](json).get shouldBe CPQ21Holder(cpq21 = CPQ21(true))
    }
    "create false from valid json" in {
      val json = Json.parse("""{"cpq21":false}""")
      Json.fromJson[CPQ21Holder](json).get shouldBe CPQ21Holder(cpq21 = CPQ21(false))
    }
  }

}

case class CPQ21Holder(cpq21: CPQ21)
