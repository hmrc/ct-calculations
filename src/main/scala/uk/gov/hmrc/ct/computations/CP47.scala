package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP47(value: Option[Int]) extends CtBoxIdentifier(name = "Disallowable entertaining") with CtOptionalInteger with Input

object CP47 {

  def apply(int: Int): CP47 = CP47(Some(int))

}


