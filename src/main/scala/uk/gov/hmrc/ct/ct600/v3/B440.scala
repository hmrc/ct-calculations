

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBigDecimal, CtBoxIdentifier, Linked}

// was B70
case class B440(value: BigDecimal) extends CtBoxIdentifier("Corporation Tax Chargeable") with CtBigDecimal

object B440 extends Linked[B430, B440] {

  override def apply(source: B430): B440 = B440(source.value)
}
