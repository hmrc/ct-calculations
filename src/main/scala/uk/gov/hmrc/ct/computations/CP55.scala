package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP55(value: Option[Int]) extends CtBoxIdentifier(name = " Employees' remuneration previously disallowed") with CtOptionalInteger with Input

object CP55 {

  def apply(int: Int): CP55 = CP55(Some(int))

}


