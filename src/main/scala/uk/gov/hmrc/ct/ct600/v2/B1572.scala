

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class B1572(value: Option[String]) extends CtBoxIdentifier("Address Line 2") with CtOptionalString with Input
