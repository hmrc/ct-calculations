package uk.gov.hmrc.ct.ct600ei.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class DIT001(value:Option[Boolean]) extends CtBoxIdentifier(name = "During the return period, did the company export goods or services outside the United Kingdom?") with CtOptionalBoolean with Input

object DIT001 {
  def apply(value: Boolean): DIT001 = DIT001(Some(value))
}