

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class HMRCFiling(value: Boolean) extends CtBoxIdentifier("HMRC filing") with CtBoolean with Input
