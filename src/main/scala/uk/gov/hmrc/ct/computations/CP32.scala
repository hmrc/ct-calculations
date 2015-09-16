package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP32(value: Option[Int]) extends CtBoxIdentifier(name = "Interest paid") with CtOptionalInteger with Input

object CP32 {

  def apply(int: Int): CP32 = CP32(Some(int))

}