package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP103(value: Option[Int]) extends CtBoxIdentifier(name = "Disallowable entertaining") with CtOptionalInteger

object CP103 extends Linked[CP48, CP103] {

  override def apply(source: CP48): CP103 = CP103(source.value)
}
