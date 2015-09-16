package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP43(value: Option[Int]) extends CtBoxIdentifier(name = "Interest Received") with CtOptionalInteger with Input

object CP43 {

  def apply(int: Int): CP43 = CP43(Some(int))

}