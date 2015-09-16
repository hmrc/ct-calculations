package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger}

case class B1(value: Int) extends CtBoxIdentifier(name = "Total turnover from trade or profession") with CtInteger
