package uk.gov.hmrc.ct.ct600a.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class LPQ06(value: Option[Boolean]) extends CtBoxIdentifier(name = "Do you intend to file your return before 30 June 2014?") with CtOptionalBoolean with Input