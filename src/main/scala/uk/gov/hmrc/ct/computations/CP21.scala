package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP21(value: Option[Int]) extends CtBoxIdentifier(name = "Legal and professional charges") with CtOptionalInteger with Input

object CP21 {

  def apply(int: Int): CP21 = CP21(Some(int))
}