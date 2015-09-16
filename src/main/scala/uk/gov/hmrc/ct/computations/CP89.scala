package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP89(value: Option[Int]) extends CtBoxIdentifier(name = "Writing Down Allowance claimed") with CtOptionalInteger with Input

object CP89 {

  def apply(value: Int): CP89 = CP89(Some(value))
}


