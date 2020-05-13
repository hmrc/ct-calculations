

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class AbbreviatedAccountsFiling(value: Boolean) extends CtBoxIdentifier("Abbreviated Accounts Filing") with CtBoolean  with Input
