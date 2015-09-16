package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP301(value: Option[Int]) extends CtBoxIdentifier(name = "Qualifying charitable donations UK") with CtOptionalInteger with Input

object CP301 {

  def apply(int: Int): CP301 = CP301(Some(int))

}


