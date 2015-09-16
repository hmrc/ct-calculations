package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{Input, CtBoolean, CtBoxIdentifier}

case class AbbreviatedAccountsFiling(value: Boolean) extends CtBoxIdentifier("Abbreviated Accounts Filing") with CtBoolean  with Input