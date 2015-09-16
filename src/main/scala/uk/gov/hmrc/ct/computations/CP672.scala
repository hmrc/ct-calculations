package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP672(value: Option[Int]) extends CtBoxIdentifier(name = "Proceeds from disposals from main pool") with CtOptionalInteger with Input

object CP672 {

  def apply(value: Int): CP672 = CP672(Some(value))
}
