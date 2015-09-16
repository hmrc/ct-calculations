package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP281(value: Option[Int]) extends CtBoxIdentifier("Losses brought forward") with CtOptionalInteger with Input

object CP281 {

  def apply(int: Int): CP281 = CP281(Some(int))
}