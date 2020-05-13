

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP7

case class B1(value: Int) extends CtBoxIdentifier(name = "Total turnover from trade or profession") with CtInteger

object B1 extends Linked[CP7, B1] {
  override def apply(source: CP7): B1 = B1(source.orZero)
}
