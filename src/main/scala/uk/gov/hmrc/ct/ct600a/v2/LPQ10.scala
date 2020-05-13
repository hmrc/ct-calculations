

package uk.gov.hmrc.ct.ct600a.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class LPQ10(value: Option[Boolean]) extends CtBoxIdentifier(name = "Any other loans?") with CtOptionalBoolean with Input
