package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP25(value: Option[Int]) extends CtBoxIdentifier(name = "Advertising and promotions") with CtOptionalInteger with Input

object CP25 {

  def apply(int: Int): CP25 = CP25(Some(int))

}