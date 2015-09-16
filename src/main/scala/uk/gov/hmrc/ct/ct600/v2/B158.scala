package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class B158(value: Option[String]) extends CtBoxIdentifier("Nominee Reference") with CtOptionalString with Input
