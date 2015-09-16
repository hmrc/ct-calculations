package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP34(value: Option[Int]) extends CtBoxIdentifier(name = "Travel and subsistence") with CtOptionalInteger with Input

object CP34 {

  def apply(int: Int): CP34 = CP34(Some(int))

}