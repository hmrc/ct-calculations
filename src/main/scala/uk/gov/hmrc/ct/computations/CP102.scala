package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP102(value: Option[Int]) extends CtBoxIdentifier(name = "Donations") with CtOptionalInteger

object CP102 extends Linked[CP47, CP102] {

  override def apply(source: CP47): CP102 = CP102(source.value)
}
