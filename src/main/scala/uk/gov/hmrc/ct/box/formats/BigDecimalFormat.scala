/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box.formats

import play.api.libs.json._
import uk.gov.hmrc.ct.box.CtBigDecimal

class BigDecimalFormat[T <: CtBigDecimal](builder: (BigDecimal => T)) extends Format[T] {

  override def reads(json: JsValue): JsResult[T] = {
     JsSuccess(builder(json.as[BigDecimal]))
   }

   override def writes(out: T): JsValue = {
     Json.toJson[BigDecimal](out.value)
   }
 }
