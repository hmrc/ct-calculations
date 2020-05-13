

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP264

case class B30(value: Int) extends CtBoxIdentifier("Trading losses of this or later accounting periods") with CtInteger

object B30 extends Linked[CP264, B30] {

  override def apply(source: CP264): B30 = B30(source.value)
}
