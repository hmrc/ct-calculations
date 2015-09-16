package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP7(value: Option[Int]) extends CtBoxIdentifier(name = "Turnover/Sales") with CtOptionalInteger with Input

object CP7 {

  def apply(int: Int): CP7 = CP7(Some(int))
}