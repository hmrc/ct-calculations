package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP100(value: Int) extends CtBoxIdentifier(name = "Profit before tax") with CtInteger

object CP100 extends Linked[CP45, CP100] {

  override def apply(source: CP45): CP100 = CP100(source.value)
}
