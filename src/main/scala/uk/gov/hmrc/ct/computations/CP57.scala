package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP57(value: Option[Int]) extends CtBoxIdentifier(name = "Income from property") with CtOptionalInteger with Input

object CP57 {

  def apply(int: Int): CP57 = CP57(Some(int))

}


