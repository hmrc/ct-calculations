/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v2.formats

import play.api.libs.json.Json
import uk.gov.hmrc.ct.ct600a.v2.{LP02, LP03}

object Loans {

  import uk.gov.hmrc.ct.ct600a.v2.formats._

  def lp02FromJsonString(json: String): LP02 = Json.fromJson[LP02](Json.parse(json)).get

  def toJsonString(lp02: LP02): String =  Json.toJson(lp02).toString()

  def asBoxString(lp02: LP02): Option[String] = Some(toJsonString(lp02))

  def lp03FromJsonString(json: String): LP03 = Json.fromJson[LP03](Json.parse(json)).get

  def toJsonString(lp03: LP03): String =  Json.toJson(lp03).toString()

  def asBoxString(lp03: LP03): Option[String] = Some(toJsonString(lp03))
}
