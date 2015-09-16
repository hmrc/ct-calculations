package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Input}

case class B155(value: String) extends CtBoxIdentifier("Your Details") with CtString with Input

