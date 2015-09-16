package uk.gov.hmrc.ct.box

import play.api.libs.json.Json

case class CtValidation(boxId: Option[String], errorMessageKey: String)

object CtValidation {

  implicit val format = Json.format[CtValidation]
}
