package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP83(value: Option[Int]) extends CtBoxIdentifier(name = "Expenditure qualifying for annual investment allowance(AIA)") with CtOptionalInteger with Input

object CP83 {

  def apply(value: Int): CP83 = CP83(Some(value))
}


