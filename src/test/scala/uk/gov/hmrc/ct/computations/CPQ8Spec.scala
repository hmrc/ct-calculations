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