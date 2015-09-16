package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}
import uk.gov.hmrc.ct.computations.CP295

// was B37
case class B315(value: Int) extends CtBoxIdentifier(name = "Profits chargeable to corporation tax") with CtInteger

object B315 extends Linked[CP295, B315] {

  override def apply(source: CP295): B315 = B315(source.value)
}
