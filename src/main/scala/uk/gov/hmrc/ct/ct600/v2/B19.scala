package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger}

case class B19(value: Int) extends CtBoxIdentifier(name = "Losses brought forward against certain investment income") with CtInteger

