package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP108(value: Option[Int]) extends CtBoxIdentifier(name = "Unpaid director's bonuses") with CtOptionalInteger

object CP108 extends Linked[CP53, CP108] {

  override def apply(source: CP53): CP108 = CP108(source.value)
}
