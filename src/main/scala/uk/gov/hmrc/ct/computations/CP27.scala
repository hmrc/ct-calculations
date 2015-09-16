package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP27(value: Option[Int]) extends CtBoxIdentifier(name = "Bank, credit card and other financial charges") with CtOptionalInteger with Input

object CP27 {

  def apply(int: Int): CP27 = CP27(Some(int))
}