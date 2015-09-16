package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class CPQ19(value: Option[Boolean]) extends CtBoxIdentifier(name = "Do you wish to claim your trading losses for this period against your profits for this period?") with CtOptionalBoolean with Input