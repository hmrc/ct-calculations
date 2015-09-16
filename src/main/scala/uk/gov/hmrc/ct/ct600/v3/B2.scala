package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtString, Input}


case class B2(value: String) extends CtBoxIdentifier("Company Registration Number (CRN)") with CtString with Input
