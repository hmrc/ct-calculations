package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class LPQ09(value: Option[Boolean]) extends CtBoxIdentifier(name = "Were any loans written off or released during this period?") with CtOptionalBoolean with Input
