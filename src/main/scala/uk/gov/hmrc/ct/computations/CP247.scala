package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}


case class CP247(value: Option[Int]) extends CtBoxIdentifier("Total balancing charges") with CtOptionalInteger

object CP247 extends Linked[CP91, CP247]{

  override def apply(source: CP91): CP247 = CP247(source.value)
}
