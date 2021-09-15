package uk.gov.hmrc.ct.ct600ei.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class DIT003(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did the company export goods or services?") with CtOptionalBoolean with Input

object DIT003 {
  def apply(value: Boolean): DIT003 = DIT003(Some(value))
}