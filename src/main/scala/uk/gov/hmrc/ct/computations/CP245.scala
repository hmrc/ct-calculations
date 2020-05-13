

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP245(value: Option[Int]) extends CtBoxIdentifier(name = "Balancing charges") with CtOptionalInteger

object CP245 extends Linked[CP96, CP245] {

  override def apply(source: CP96): CP245 = CP245(source.value)
}
