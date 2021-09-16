/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box.formats
import org.joda.time.LocalDate
import play.api.libs.json.JodaReads._
import play.api.libs.json.JodaWrites._
import play.api.libs.json._
import uk.gov.hmrc.ct.box.CtDate

class  DateFormat[T <: CtDate](builder: (LocalDate => T)) extends Format[T] {

  override def reads(json: JsValue): JsResult[T] = {
    JsSuccess(builder(json.as[LocalDate]))
  }

  override def writes(o: T): JsValue = {
    Json.toJson[LocalDate](o.value)
  }
}
