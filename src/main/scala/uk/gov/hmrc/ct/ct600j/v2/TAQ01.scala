

package uk.gov.hmrc.ct.ct600j.v2

import play.api.libs.json.Format
import uk.gov.hmrc.ct.box.formats.BooleanFormat
import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class TAQ01(value: Boolean) extends CtBoxIdentifier("Has tax avoidance") with CtBoolean

object TAQ01 extends Input {

  implicit val TAQ01Format: Format[TAQ01] = new BooleanFormat[TAQ01](TAQ01.apply)

}
