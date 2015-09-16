package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP87Input(value: Option[Int]) extends CtBoxIdentifier(name = "First year allowance claimed") with CtOptionalInteger with Input

object CP87Input {

  def apply(int: Int): CP87Input = CP87Input(Some(int))

}