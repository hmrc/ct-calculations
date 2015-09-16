package uk.gov.hmrc.ct.ct600a.v3.formats

import play.api.libs.json.Json
import uk.gov.hmrc.ct.ct600a.v3.{formats, A25, A10}

object Loans {

  import uk.gov.hmrc.ct.ct600a.v3.formats._

  def A10FromJsonString(json: String): A10 = Json.fromJson[A10](Json.parse(json)).get

  def toJsonString(a10: A10): String =  Json.toJson(a10).toString() //LP02 in v2

  def asBoxString(a10: A10): Option[String] = Some(toJsonString(a10)) // LP02 in v2

  def A25FromJsonString(json: String): A25 = Json.fromJson[A25](Json.parse(json)).get

  def toJsonString(a25: A25): String =  Json.toJson(a25).toString() // LP03 in v2

  def asBoxString(a25: A25): Option[String] = Some(toJsonString(a25))
}
