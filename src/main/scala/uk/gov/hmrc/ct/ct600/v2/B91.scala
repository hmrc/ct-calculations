package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBigDecimal, CtBoxIdentifier, Input}


case class B91(value: BigDecimal) extends CtBoxIdentifier("Corporation Tax already paid (and not already repaid)") with CtBigDecimal with Input

