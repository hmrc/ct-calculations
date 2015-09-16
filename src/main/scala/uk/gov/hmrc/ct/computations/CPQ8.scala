package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class CPQ8(value: Option[Boolean]) extends CtBoxIdentifier(name = "Did the company cease trading?") with CtOptionalBoolean with Input
