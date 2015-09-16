package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP668(value: Option[Int]) extends CtBoxIdentifier(name = "Writing down allowance claimed from special rate pool") with CtOptionalInteger with Input

object CP668 {

  def apply(value: Int): CP668 = CP668(Some(value))
}
