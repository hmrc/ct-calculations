package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtOptionalInteger, CtBoxIdentifier, Linked}
import uk.gov.hmrc.ct.computations.CP257

case class B160(value: Option[Int]) extends CtBoxIdentifier(name = "Trading losses brought forward claimed against profits") with CtOptionalInteger

object B160 extends Linked[CP257, B160] {

  override def apply(source: CP257): B160 = B160(source.value)
}