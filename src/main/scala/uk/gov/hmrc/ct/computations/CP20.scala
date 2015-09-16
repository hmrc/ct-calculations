package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP20(value: Option[Int]) extends CtBoxIdentifier(name = "Consultancy") with CtOptionalInteger with Input

object CP20 {

  def apply(int: Int): CP20 = CP20(Some(int))

}