package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Input}


case class B3(value: String) extends CtBoxIdentifier("Tax Reference Number") with CtString with Input
