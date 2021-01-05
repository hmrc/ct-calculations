/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box.formats

import play.api.libs.json._
import uk.gov.hmrc.ct.box.CtInteger

class IntegerFormat[T <: CtInteger](builder: (Int => T)) extends Format[T] {
   override def reads(json: JsValue): JsResult[T] = {
     JsSuccess(builder(json.as[Int]))
   }

   override def writes(out: T): JsValue = {
     Json.toJson[Int](out.value)
   }
 }
