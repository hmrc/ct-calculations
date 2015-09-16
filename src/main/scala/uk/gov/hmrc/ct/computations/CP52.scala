package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP52(value: Option[Int]) extends CtBoxIdentifier(name = "Penalties and fines") with CtOptionalInteger with Input

object CP52 {

  def apply(int: Int): CP52 = CP52(Some(int))

}


