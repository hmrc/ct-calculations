package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP237(value: Option[Int]) extends CtBoxIdentifier with CtOptionalInteger

object CP237 extends Linked[CP287, CP237] {

  override def apply(source: CP287): CP237 = CP237(source.value)
}
