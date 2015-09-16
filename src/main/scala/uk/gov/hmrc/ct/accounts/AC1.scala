package uk.gov.hmrc.ct.accounts

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class AC1(value: Option[String]) extends CtBoxIdentifier(name = "Company Registration Number") with CtOptionalString with Input
