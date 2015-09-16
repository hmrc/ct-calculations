package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP29(value: Option[Int]) extends CtBoxIdentifier(name = "Donations") with CtOptionalInteger with Input

object CP29 {

  def apply(int: Int): CP29 = CP29(Some(int))

}