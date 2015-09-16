package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP252

case class B174(value: Int) extends CtBoxIdentifier("Designated environmentally friendly Machinery and Plant") with CtInteger

object B174 extends Linked[CP252, B174] {

  override def apply(source: CP252): B174 = B174(source.value)
}
