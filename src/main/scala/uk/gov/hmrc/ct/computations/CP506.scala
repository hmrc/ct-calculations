

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input, Linked}

case class CP506(value: Option[Int]) extends CtBoxIdentifier(name = "Ancillary income") with CtOptionalInteger with Input

object CP506 extends Linked[CP502, CP506] {

  override def apply(source: CP502): CP506 = CP506(source.value)
}
