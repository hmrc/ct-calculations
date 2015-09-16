package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP86(value: Option[Int]) extends CtBoxIdentifier(name = "Other first year allowances claimed") with CtOptionalInteger with Input

object CP86 {

  def apply(int: Int): CP86 = CP86(Some(int))

}


