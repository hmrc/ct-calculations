package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP80(value: Option[Int]) extends CtBoxIdentifier(name = "Other (FYA) expenditure") with CtOptionalInteger with Input

object CP80 {

  def apply(value: Int): CP80 = CP80(Some(value))
}


