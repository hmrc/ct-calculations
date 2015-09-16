package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP302(value: Option[Int]) extends CtBoxIdentifier(name = "Qualifying charitable donations EEA") with CtOptionalInteger with Input

object CP302 {

  def apply(int: Int): CP302 = CP302(Some(int))

}


