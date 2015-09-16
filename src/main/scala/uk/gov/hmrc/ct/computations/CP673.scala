package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP673(value: Option[Int]) extends CtBoxIdentifier(name = "Market value of unsold assets") with CtOptionalInteger with Input

object CP673 {

  def apply(value: Int): CP673 = CP673(Some(value))
}
