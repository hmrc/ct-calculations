package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Input, CtBoolean, CtBoxIdentifier}

case class HMRCFiling(value: Boolean) extends CtBoxIdentifier("HMRC filing") with CtBoolean with Input

