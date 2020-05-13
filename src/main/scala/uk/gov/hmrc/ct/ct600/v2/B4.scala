

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP257


case class B4(value: Option[Int]) extends CtBoxIdentifier("Trading losses brought forward set against trading profits") with CtOptionalInteger

object B4 extends Linked[CP257, B4] {

  override def apply(source: CP257): B4 = B4(source.value)
}
