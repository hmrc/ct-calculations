package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Input}


case class B4(value: String) extends CtBoxIdentifier("Type of Company") with CtString with Input
