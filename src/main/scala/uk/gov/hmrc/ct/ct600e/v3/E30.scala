package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class E30(value: Option[String]) extends CtBoxIdentifier("Claimer's name") with CtOptionalString with Input
