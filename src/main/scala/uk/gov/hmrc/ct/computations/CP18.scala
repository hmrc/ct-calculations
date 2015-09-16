package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP18(value: Option[Int]) extends CtBoxIdentifier(name = "Subcontractors' payments (construction industry only)") with CtOptionalInteger with Input

object CP18 {

  def apply(int: Int): CP18 = CP18(Some(int))
}