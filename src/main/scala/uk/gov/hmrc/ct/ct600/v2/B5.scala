package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP258

case class B5(value: Int) extends CtBoxIdentifier("Net Trading and professional profits") with CtInteger

object B5 extends Linked[CP258, B5] {

  override def apply(source: CP258): B5 = B5(source.value)
}

