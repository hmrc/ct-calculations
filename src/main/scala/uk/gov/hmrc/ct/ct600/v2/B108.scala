package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}
import uk.gov.hmrc.ct.computations.CP291


case class B108(value: Option[Int]) extends CtBoxIdentifier("Balancing charges (Machinery & Plant - main pool)") with CtOptionalInteger

object B108 extends Linked[CP291, B108] {

  override def apply(source: CP291): B108 = B108(source.value)
}
