package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP19(value: Option[Int]) extends CtBoxIdentifier(name = "Accountancy and audit") with CtOptionalInteger with Input

object CP19 {

  def apply(int: Int): CP19 = CP19(Some(int))

}