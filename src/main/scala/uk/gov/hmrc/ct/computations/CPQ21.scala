package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class CPQ21(value: Boolean) extends CtBoxIdentifier(name = "Donations made?") with CtBoolean with Input
