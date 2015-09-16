package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP501(value: Option[Int]) extends CtBoxIdentifier(name = "Gross income from property") with CtOptionalInteger with Input

object CP501 {

  def apply(int: Int): CP501 = CP501(Some(int))

}


