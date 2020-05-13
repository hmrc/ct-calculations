

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBigDecimal, CtBoxIdentifier, Input}


case class B84(value: BigDecimal) extends CtBoxIdentifier("Income Tax deducted from gross income included in profits") with CtBigDecimal with Input
