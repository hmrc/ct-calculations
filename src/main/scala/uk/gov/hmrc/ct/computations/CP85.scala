package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP85(value: Option[Int]) extends CtBoxIdentifier(name = "Relevant first year allowances claimed") with CtOptionalInteger with Input

object CP85 {

  def apply(int: Int): CP85 = CP85(Some(int))

}


