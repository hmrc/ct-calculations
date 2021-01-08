/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600a.v3.formats

import play.api.libs.json.Json
import uk.gov.hmrc.ct.ct600a.v3.LoansToParticipators

object LoansFormatter {

  import uk.gov.hmrc.ct.ct600a.v3.formats._

  def LoansFromJsonString(json: String): LoansToParticipators = Json.fromJson[LoansToParticipators](Json.parse(json)).get

  def toJsonString(loans2p: LoansToParticipators): String =  Json.toJson(loans2p).toString()

  def asBoxString(loans2p: LoansToParticipators): Option[String] = Some(toJsonString(loans2p))

}
