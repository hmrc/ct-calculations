

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP305

case class B35(value: Int) extends CtBoxIdentifier("Qualifying Charitable Donations") with CtInteger

object B35 extends Linked[CP305, B35] {

  override def apply(source: CP305): B35 = B35(source.value)
}
