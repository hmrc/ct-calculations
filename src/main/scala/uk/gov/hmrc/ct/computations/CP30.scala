package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP30(value: Option[Int]) extends CtBoxIdentifier(name = "Entertaining") with CtOptionalInteger with Input

object CP30 {

  def apply(int: Int): CP30 = CP30(Some(int))

}