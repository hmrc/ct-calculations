package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class B139(value: Boolean) extends CtBoxIdentifier("Do you want HMRC to retain repayments of £20 or less?") with CtBoolean with Input

