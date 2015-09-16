package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP84(value: Option[Int]) extends CtBoxIdentifier(name = "Disposal proceeds") with CtOptionalInteger with Input

object CP84 {

  def apply(value: Int): CP84 = CP84(Some(value))
}


