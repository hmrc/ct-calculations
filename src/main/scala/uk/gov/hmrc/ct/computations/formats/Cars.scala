package uk.gov.hmrc.ct.computations.formats

import play.api.libs.json.Json
import uk.gov.hmrc.ct.computations.LEC01

object Cars {

  def lec01FromJsonString(json: String): LEC01 = Json.fromJson[LEC01](Json.parse(json)).get

  def toJsonString(lec01: LEC01): String =  Json.toJson(lec01).toString()

  def asBoxString(lec01: LEC01): Option[String] = Some(toJsonString(lec01))
}
