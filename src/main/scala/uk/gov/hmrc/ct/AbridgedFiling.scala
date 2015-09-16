package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Input, CtBoolean, CtBoxIdentifier}

case class AbridgedFiling(value: Boolean) extends CtBoxIdentifier("Abridged Filing") with CtBoolean with Input