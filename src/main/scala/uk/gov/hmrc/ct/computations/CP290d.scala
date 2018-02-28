package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Linked}

case class CP290d(value: Option[Int]) extends CtBoxIdentifier(name = "Post 1/4/17 main land losses brought forward against Northern Ireland TP") with CtOptionalInteger

object CP290d extends Linked[CP283d, CP290d] {
  def apply(source: CP283d): CP290d = CP290d(source.value)
}
