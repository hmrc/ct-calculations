package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP51(value: Option[Int]) extends CtBoxIdentifier(name = "Net loss on sale of fixed assets") with CtOptionalInteger with Input

object CP51 {

  def apply(int: Int): CP51 = CP51(Some(int))

}


