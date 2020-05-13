

package uk.gov.hmrc.ct.box.formats

import play.api.libs.json._
import uk.gov.hmrc.ct.box.CtString

class StringFormat[T <: CtString](builder: (String => T)) extends Format[T] {
   override def reads(json: JsValue): JsResult[T] = {
     JsSuccess(builder(json.as[String]))
   }

   override def writes(out: T): JsValue = {
     Json.toJson[String](out.value)
   }
 }
