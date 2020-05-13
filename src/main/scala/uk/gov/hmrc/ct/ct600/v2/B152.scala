

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class B152(value: Option[String]) extends CtBoxIdentifier("Name of Account") with CtOptionalString with Input
