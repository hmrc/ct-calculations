package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class E10(value: Option[String]) extends CtBoxIdentifier("Charity Commission registration number, or OSCR number (if applicable)") with CtOptionalString with Input
