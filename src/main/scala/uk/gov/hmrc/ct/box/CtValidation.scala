

package uk.gov.hmrc.ct.box

import play.api.libs.json.Json

case class CtValidation(boxId: Option[String], errorMessageKey: String, args:Option[Seq[String]] = None) {
  def isGlobalError = boxId.isEmpty
}

object CtValidation {

 lazy implicit val format = Json.format[CtValidation]
}
