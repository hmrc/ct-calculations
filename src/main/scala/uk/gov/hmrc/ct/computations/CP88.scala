package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP88(value: Option[Int]) extends CtBoxIdentifier(name = "Annual Investment Allowance") with CtOptionalInteger with Input

object CP88 {

  def apply(value: Int): CP88 = CP88(Some(value))
}


