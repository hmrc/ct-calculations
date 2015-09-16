package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBigDecimal, Linked}
import uk.gov.hmrc.ct.ct600a.v2.A13

case class B79(value: Option[BigDecimal]) extends CtBoxIdentifier("B79 - Tax payable under S419 ICTA 1988") with CtOptionalBigDecimal

object B79 extends Linked[A13, B79] {

  override def apply(source: A13): B79 = B79(source.value)
}
