/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.formats

import play.api.libs.json.Json
import uk.gov.hmrc.ct.computations.SBA01

object Buildings {

  def sba01FromJsonString(json: String): SBA01 = Json.fromJson[SBA01](Json.parse(json)).get

  def toJsonString(sba01: SBA01): String =  Json.toJson(sba01).toString()

  def asBoxString(sba01: SBA01): Option[String] = Some(toJsonString(sba01))
}
