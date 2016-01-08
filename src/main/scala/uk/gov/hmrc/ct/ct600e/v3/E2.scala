package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{Input, CtString, CtBoxIdentifier}

case class E2(value: String) extends CtBoxIdentifier("Tax Reference - UTR") with CtString with Input
