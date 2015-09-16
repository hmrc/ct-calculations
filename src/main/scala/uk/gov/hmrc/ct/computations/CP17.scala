package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP17(value: Option[Int]) extends CtBoxIdentifier(name = "Salaries and wages") with CtOptionalInteger with Input

object CP17 {

  def apply(int: Int): CP17 = CP17(Some(int))
}
