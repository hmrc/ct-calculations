

package uk.gov.hmrc.ct.computations

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtInteger, Input}

case class CP111(value: Int) extends CtBoxIdentifier(name = "Employees remuneration previously disallowed") with CtInteger with Input
