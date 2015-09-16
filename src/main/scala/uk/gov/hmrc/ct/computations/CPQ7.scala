package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class CPQ7(value: Boolean) extends CtBoxIdentifier(name = "Claim trade capital allowances or report balancing charge or incur qualifying expenditure.") with CtBoolean with Input