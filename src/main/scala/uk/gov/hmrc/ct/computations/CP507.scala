

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Linked}

case class CP507(value: Int) extends CtBoxIdentifier(name = "Income from property") with CtInteger

object CP507 extends Linked[CP501, CP507] {

  override def apply(source: CP501): CP507 = CP507(source.orZero)
}
