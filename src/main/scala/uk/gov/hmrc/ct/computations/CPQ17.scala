package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class CPQ17(value: Option[Boolean]) extends CtBoxIdentifier(name = "Trading losses not used from previous accounting periods.?") with CtOptionalBoolean with Input