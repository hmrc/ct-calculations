package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP33(value: Option[Int]) extends CtBoxIdentifier(name = "Profit/Losses on disposal of assets") with CtOptionalInteger with Input

object CP33 {

  def apply(int: Int): CP33 = CP33(Some(int))

}