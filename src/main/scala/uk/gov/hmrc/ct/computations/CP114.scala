package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP114(value: Int) extends CtBoxIdentifier(name = "Non-trade interest received") with CtInteger

object CP114 extends Linked[CP58, CP114] {

  override def apply(source: CP58): CP114 = CP114(source.value)
}


