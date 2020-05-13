

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP234(value: Option[Int]) extends CtBoxIdentifier with CtOptionalInteger

object CP234 extends Linked[CP281, CP234] {

  override def apply(source: CP281): CP234 = CP234(source.value)
}
