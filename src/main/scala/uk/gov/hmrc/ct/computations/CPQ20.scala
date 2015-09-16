package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class CPQ20(value: Option[Boolean]) extends CtBoxIdentifier(name = "Is the company claiming to carry losses of this period back to an earlier period") with CtOptionalBoolean with Input
