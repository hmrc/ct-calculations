

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP107(value: Option[Int]) extends CtBoxIdentifier(name = "Penalties and fines") with CtOptionalInteger

object CP107 extends Linked[CP52, CP107] {

  override def apply(source: CP52): CP107 = CP107(source.value)
}
