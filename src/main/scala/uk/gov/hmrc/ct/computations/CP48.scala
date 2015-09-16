package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP48(value: Option[Int]) extends CtBoxIdentifier(name = "Donations") with CtOptionalInteger with Input

object CP48 {

  def apply(int: Int): CP48 = CP48(Some(int))

}


