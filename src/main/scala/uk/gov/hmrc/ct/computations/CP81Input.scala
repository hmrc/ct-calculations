package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP81Input(value: Option[Int]) extends CtBoxIdentifier(name = "Expenditure qualifying for first year allowance (FYA)") with CtOptionalInteger with Input

object CP81Input {

  def apply(int: Int): CP81Input = CP81Input(Some(int))

}