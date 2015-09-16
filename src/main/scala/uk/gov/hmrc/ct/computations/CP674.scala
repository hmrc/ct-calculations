package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP674(value: Option[Int]) extends CtBoxIdentifier(name = "Total additions") with CtOptionalInteger with Input

object CP674 {

  def apply(value: Int): CP674 = CP674(Some(value))
}
