

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger}

case class B20(value: Int) extends CtBoxIdentifier(name = "Non-trade deficits on loan relationships (including interest) and derivative contracts (financial instruments) brought forward set against non-trading profits") with CtInteger
