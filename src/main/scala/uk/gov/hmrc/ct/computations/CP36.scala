package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP36(value: Option[Int]) extends CtBoxIdentifier(name = "Administration and office expenses") with CtOptionalInteger with Input

object CP36 {

  def apply(int: Int): CP36 = CP36(Some(int))

}