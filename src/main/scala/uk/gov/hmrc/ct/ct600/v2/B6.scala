

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP259

case class B6(value: Int) extends CtBoxIdentifier("Profits and Gains from non-trading loan relationships") with CtInteger

object B6 extends Linked[CP259, B6] {

  override def apply(source: CP259): B6 = B6(source.value)
}
