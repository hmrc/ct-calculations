

package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBigDecimal, CtBoxIdentifier, Linked}

// was B78
case class B475(value: BigDecimal) extends CtBoxIdentifier("Net Corporation Tax liability") with CtBigDecimal

object B475 extends Linked[B440, B475] {

  override def apply(source: B440): B475 = B475(source.value)
}
