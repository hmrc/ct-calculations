package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP46(value: Option[Int]) extends CtBoxIdentifier(name = "Depreciation") with CtOptionalInteger with Input

object CP46 {

  def apply(int: Int): CP46 = CP46(Some(int))

}


