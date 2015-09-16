package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP79(value: Option[Int]) extends CtBoxIdentifier(name = "Relevant first year allowance (FYA) expenditure") with CtOptionalInteger with Input

object CP79 {

  def apply(value: Int): CP79 = CP79(Some(value))
}


