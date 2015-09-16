package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP508(value: Int) extends CtBoxIdentifier(name = "Income from property expenses") with CtInteger

object CP508 extends Linked[CP503, CP508] {

  override def apply(source: CP503): CP508 = CP508(source.value.getOrElse(0))
}


