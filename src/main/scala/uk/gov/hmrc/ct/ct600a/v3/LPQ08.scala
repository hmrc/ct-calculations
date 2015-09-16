package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class LPQ08(value: Option[Boolean]) extends CtBoxIdentifier(name = "Were any loans written off or released after the end of this period?") with CtOptionalBoolean with Input
