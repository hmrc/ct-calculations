package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger}

case class B8(value: Int) extends CtBoxIdentifier(name = "Annuities, annual payments and discounts not arising from loan relationships and from which income tax has not been deducted") with CtInteger

