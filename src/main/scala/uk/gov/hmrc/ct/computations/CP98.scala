package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}


case class CP98(value: Option[Int]) extends CtBoxIdentifier("Total Charges") with CtOptionalInteger

object CP98 extends Linked[CP96, CP98]{

  override def apply(source: CP96): CP98 = CP98(source.value)
}
