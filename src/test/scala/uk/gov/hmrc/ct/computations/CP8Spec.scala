/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations

import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json
import uk.gov.hmrc.ct.box.CtValidation
import uk.gov.hmrc.ct.computations.formats._
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever
import  uk.gov.hmrc.ct.utils.CatoInputBounds._

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
    "fail when negative" in {
      CP8(Some(-99999999)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP8"), "error.CP8.mustBeZeroOrPositive"))
    }
    "fail when below min and negative" in {
      CP8(Some(-100000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP8"), "error.CP8.below.min", Some(Seq(oldMinWithCommas, oldMaxWithCommas))), CtValidation(Some("CP8"),"error.CP8.mustBeZeroOrPositive"))
    }
    "fail when above max" in {
      CP8(Some(100000000)).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP8"), "error.CP8.above.max", Some(Seq(oldMinWithCommas, oldMaxWithCommas))))
    }
    "fail when empty" in {
      CP8(None).validate(boxRetriever) shouldBe Set(CtValidation(Some("CP8"), "error.CP8.required"))
    }
  }
  
}

case class CP8Holder(cp8: CP8)
