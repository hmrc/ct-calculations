

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger}

case class B10(value: Int) extends CtBoxIdentifier(name = "Income from which income tax has been deducted") with CtInteger
