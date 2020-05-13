

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP256

case class B155(value: Int) extends CtBoxIdentifier(name = "Trading profits") with CtInteger

object B155 extends Linked[CP256, B155] {

  override def apply(source: CP256): B155 = B155(source.value)
}
