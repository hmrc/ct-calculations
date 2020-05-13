

package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class B1571(value: Option[String]) extends CtBoxIdentifier("Address Line 1") with CtOptionalString with Input
