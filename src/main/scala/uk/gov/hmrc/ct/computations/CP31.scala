package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP31(value: Option[Int]) extends CtBoxIdentifier(name = "Insurance") with CtOptionalInteger with Input

object CP31 {

  def apply(int: Int): CP31 = CP31(Some(int))
}