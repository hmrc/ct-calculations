package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP53(value: Option[Int]) extends CtBoxIdentifier(name = "Unpaid employees' remuneration") with CtOptionalInteger with Input

object CP53 {

  def apply(int: Int): CP53 = CP53(Some(int))

}


