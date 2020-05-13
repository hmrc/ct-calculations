

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class B151(value: Option[String]) extends CtBoxIdentifier("Account number") with CtOptionalString with Input
