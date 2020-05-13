

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger}

case class B16(value: Int) extends CtBoxIdentifier(name = "Gross chargeable gains") with CtInteger
