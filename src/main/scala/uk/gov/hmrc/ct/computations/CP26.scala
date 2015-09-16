package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP26(value: Option[Int]) extends CtBoxIdentifier(name = " Bad debts") with CtOptionalInteger with Input

object CP26 {

  def apply(int: Int): CP26 = CP26(Some(int))

}