

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean}

case class B2(value: Option[Boolean]) extends CtBoxIdentifier(name = "Banks, building societies, insurance companies and other financial concerns") with CtOptionalBoolean
