package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP23(value: Option[Int]) extends CtBoxIdentifier(name = "Rent and rates") with CtOptionalInteger with Input

object CP23 {

  def apply(int: Int): CP23 = CP23(Some(int))

}