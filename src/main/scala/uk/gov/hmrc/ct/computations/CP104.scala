package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP104(value: Option[Int]) extends CtBoxIdentifier(name = "Legal professional fees") with CtOptionalInteger

object CP104 extends Linked[CP49, CP104] {

  override def apply(source: CP49): CP104 = CP104(source.value)
}
