

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP258

case class B165(value: Int) extends CtBoxIdentifier(name = "Net trading profits") with CtInteger

object B165 extends Linked[CP258, B165] {

  override def apply(source: CP258): B165 = B165(source.value)
}
