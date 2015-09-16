package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class CPQ18(value: Option[Boolean]) extends CtBoxIdentifier(name = "Claim any trading losses carried back from a later period against profits in this period.") with CtOptionalBoolean with Input
