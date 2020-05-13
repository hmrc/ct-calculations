

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP239(value: Int) extends CtBoxIdentifier(name = "Losses used against total profits") with CtInteger

object CP239 extends Linked[CP294, CP239] {

  override def apply(source: CP294): CP239 = CP239(source.value)
}
