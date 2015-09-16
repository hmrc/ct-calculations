package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP667(value: Option[Int]) extends CtBoxIdentifier(name = "Proceeds from disposals from special rate pool") with CtOptionalInteger with Input

object CP667 {

  def apply(value: Int): CP667 = CP667(Some(value))
}
