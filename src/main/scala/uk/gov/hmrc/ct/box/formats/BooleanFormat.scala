/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box.formats

import play.api.libs.json._
import uk.gov.hmrc.ct.box.CtBoolean

class BooleanFormat[T <: CtBoolean](builder: (Boolean => T)) extends Format[T] {
   override def reads(json: JsValue): JsResult[T] = {
     JsSuccess(builder(json.as[Boolean]))
   }

   override def writes(out: T): JsValue = {
     Json.toJson[Boolean](out.value)
   }
 }
