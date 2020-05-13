

package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoolean, CtBoxIdentifier, Input}

case class CATO10(value: Boolean) extends CtBoxIdentifier(name = "Declaration Agreement") with CtBoolean with Input
