package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP287(value: Option[Int]) extends CtBoxIdentifier(name = "Amount of loss carried back to earlier periods") with CtOptionalInteger with Input

object CP287 {

  def apply(int: Int): CP287 = CP287(Some(int))
}