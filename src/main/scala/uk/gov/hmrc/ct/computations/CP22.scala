package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP22(value: Option[Int]) extends CtBoxIdentifier(name = "Light, heat and power") with CtOptionalInteger with Input

object CP22 {

  def apply(int: Int): CP22 = CP22(Some(int))
}
