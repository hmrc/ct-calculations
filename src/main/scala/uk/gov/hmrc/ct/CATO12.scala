package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class CATO12(value: Option[String]) extends CtBoxIdentifier(name = "Declaration Authorising Person Full Name") with CtOptionalString with Input

object CATO12 {
  def apply(str: String): CATO12 = CATO12(Some(str))
}
