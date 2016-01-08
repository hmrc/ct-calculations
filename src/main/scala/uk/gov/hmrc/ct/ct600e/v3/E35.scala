package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class E35(value: Option[String]) extends CtBoxIdentifier("Claimer's status") with CtOptionalString with Input
