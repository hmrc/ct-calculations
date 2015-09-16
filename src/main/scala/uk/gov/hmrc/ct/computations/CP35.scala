package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP35(value: Option[Int]) extends CtBoxIdentifier(name = "Vehicle expenses") with CtOptionalInteger with Input

object CP35 {

  def apply(int: Int): CP35 = CP35(Some(int))
}