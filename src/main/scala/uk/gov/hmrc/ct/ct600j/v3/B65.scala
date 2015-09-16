package uk.gov.hmrc.ct.ct600j.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}


case class B65(value: Option[Boolean]) extends CtBoxIdentifier("Notice of disclosable avoidance schemes") with CtOptionalBoolean with Input
