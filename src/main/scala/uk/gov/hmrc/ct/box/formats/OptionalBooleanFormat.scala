

package uk.gov.hmrc.ct.box.formats

import play.api.libs.json._
import uk.gov.hmrc.ct.box.CtOptionalBoolean

class OptionalBooleanFormat[T <: CtOptionalBoolean](builder: (Option[Boolean] => T)) extends Format[T] {
   override def reads(json: JsValue): JsResult[T] = {
     JsSuccess(builder(json.asOpt[Boolean]))
   }

   override def writes(out: T): JsValue = {
     Json.toJson[Option[Boolean]](out.value)
   }
 }
