package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP95(value: Option[Int]) extends CtBoxIdentifier("Total Allowances") with CtOptionalInteger

object CP95 extends Linked[CP93, CP95]{

  override def apply(source: CP93): CP95 = CP95(source.value)
}
