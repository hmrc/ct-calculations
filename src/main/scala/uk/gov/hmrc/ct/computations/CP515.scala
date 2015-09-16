package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP515(value: Option[Int]) extends CtBoxIdentifier with CtOptionalInteger

object CP515 extends Linked[CP513, CP515]{

  override def apply(source: CP513): CP515 = CP515(source.value)
}
