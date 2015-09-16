package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger}

case class CP303(value: Option[Int]) extends CtBoxIdentifier(name = "Non qualifying charitable donations") with CtOptionalInteger

object CP303 {

  def apply(int: Int): CP303 = CP303(Some(int))

}
