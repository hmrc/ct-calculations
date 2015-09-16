package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP49(value: Option[Int]) extends CtBoxIdentifier(name = "Legal and professional fees") with CtOptionalInteger with Input

object CP49 {

  def apply(int: Int): CP49 = CP49(Some(int))

}


