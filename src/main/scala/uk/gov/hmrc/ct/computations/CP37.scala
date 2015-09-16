package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP37(value: Option[Int]) extends CtBoxIdentifier(name = "Sundry expenses (use only for small miscellaneous expenses)") with CtOptionalInteger with Input

object CP37 {

  def apply(int: Int): CP37 = CP37(Some(int))

}