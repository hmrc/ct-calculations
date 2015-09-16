package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP24(value: Option[Int]) extends CtBoxIdentifier(name = "Repairs, renewals and maintenance") with CtOptionalInteger with Input

object CP24 {

  def apply(int: Int): CP24 = CP24(Some(int))

}
