/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.box.formats

import play.api.libs.json._
import uk.gov.hmrc.ct.box.CtOptionalInteger

class OptionalIntegerFormat[T <: CtOptionalInteger](builder: (Option[Int] => T)) extends Format[T] {
  override def reads(json: JsValue): JsResult[T] = {
    JsSuccess(builder(json.asOpt[Int]))
  }

  override def writes(out: T): JsValue = {
    out.value.fold[JsValue](JsNull)(x => Json.toJson[Int](x))
  }
}
