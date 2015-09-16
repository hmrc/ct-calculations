package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP82(value: Option[Int]) extends CtBoxIdentifier(name = "Additions qualifying for writing down allowance") with CtOptionalInteger with Input

object CP82 {

  def apply(value: Int): CP82 = CP82(Some(value))
}


