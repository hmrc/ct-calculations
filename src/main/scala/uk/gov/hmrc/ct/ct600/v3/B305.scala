

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP305

case class B305(value: Int) extends CtBoxIdentifier(name = "Qualifying donations") with CtInteger

object B305 extends Linked[CP305, B305] {

  override def apply(source: CP305): B305 = B305(source.value)
}
