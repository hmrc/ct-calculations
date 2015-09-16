package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP28(value: Option[Int]) extends CtBoxIdentifier(name = "Depreciation") with CtOptionalInteger with Input

object CP28 {

  def apply(int: Int): CP28 = CP28(Some(int))

}