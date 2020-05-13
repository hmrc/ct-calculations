

package uk.gov.hmrc.ct.ct600e.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class E1011(value: Option[Boolean]) extends CtBoxIdentifier("All exempt") with CtOptionalBoolean with Input
