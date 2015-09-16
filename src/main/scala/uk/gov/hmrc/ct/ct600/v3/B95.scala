package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtOptionalBoolean, CtBoxIdentifier, Input}


case class B95(value: Option[Boolean]) extends CtBoxIdentifier("Loans and arrangements to participators by close companies") with CtOptionalBoolean with Input
