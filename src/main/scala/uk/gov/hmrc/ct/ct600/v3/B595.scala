package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBigDecimal, CtBoxIdentifier, Input}


case class B595(value: BigDecimal) extends CtBoxIdentifier("Tax already paid ( and not already repaid)") with CtBigDecimal with Input
