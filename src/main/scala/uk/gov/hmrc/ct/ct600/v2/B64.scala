

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.CATO04
import uk.gov.hmrc.ct.box.{CtBigDecimal, CtBoxIdentifier, Linked}

case class B64(value: BigDecimal) extends CtBoxIdentifier("Marginal Rate Relief") with CtBigDecimal

object B64 extends Linked[CATO04, B64] {

  override def apply(source: CATO04): B64 = B64(source.value)
}
