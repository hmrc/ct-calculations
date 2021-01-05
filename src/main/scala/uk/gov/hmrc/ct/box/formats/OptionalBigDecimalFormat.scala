/*
 * Copyright 2020 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box.formats

import play.api.libs.json._
import uk.gov.hmrc.ct.box.CtOptionalBigDecimal

class OptionalBigDecimalFormat[T <: CtOptionalBigDecimal](builder: (Option[BigDecimal] => T)) extends Format[T] {
  override def reads(json: JsValue): JsResult[T] = {
    JsSuccess(builder(json.asOpt[BigDecimal]))
  }

  override def writes(out: T): JsValue = {
    out.value.fold[JsValue](JsNull)(x => Json.toJson[BigDecimal](x))
  }
}
