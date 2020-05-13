

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger}

case class B13(value: Int) extends CtBoxIdentifier(name = "Tonnage tax profits") with CtInteger
