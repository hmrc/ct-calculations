package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{Input, CtBoxIdentifier, CtOptionalInteger}

case class CP15(value: Option[Int]) extends CtBoxIdentifier(name = "Directors Pension") with CtOptionalInteger with Input

object CP15 {

  def apply(int: Int): CP15 = CP15(Some(int))
}
