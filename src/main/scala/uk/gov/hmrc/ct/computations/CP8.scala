package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP8(value: Option[Int]) extends CtBoxIdentifier(name = "Cost Of Sales") with CtOptionalInteger with Input

object CP8 {

  def apply(int: Int): CP8 = CP8(Some(int))
}
