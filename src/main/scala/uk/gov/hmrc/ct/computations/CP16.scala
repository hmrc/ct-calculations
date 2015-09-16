package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class CP16(value: Option[Int]) extends CtBoxIdentifier(name = "Directors’ remuneration") with CtOptionalInteger with Input

object CP16 {

  def apply(int: Int): CP16 = CP16(Some(int))
}