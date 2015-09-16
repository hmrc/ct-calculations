package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{Linked, CtOptionalInteger, CtBoxIdentifier}
import uk.gov.hmrc.ct.computations.CP670

case class B106(value: Option[Int]) extends CtBoxIdentifier("Balancing charges(Machinery & Plant - special rate pool)") with CtOptionalInteger

object B106 extends Linked[CP670, B106] {

  override def apply(source: CP670): B106 = B106(source.value)
}