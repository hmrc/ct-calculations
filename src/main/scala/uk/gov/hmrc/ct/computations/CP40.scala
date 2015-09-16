package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP40(value: Int) extends CtBoxIdentifier with CtInteger

object CP40 extends Linked[CP38, CP40] {

  override def apply(source: CP38): CP40 = CP40(source.value)
}
