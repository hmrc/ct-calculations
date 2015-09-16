package uk.gov.hmrc.ct

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}

case class CATO11(value: Option[String]) extends CtBoxIdentifier(name = "Declaration Authorising Person Status") with CtOptionalString with Input

object CATO11 {

  def apply(str: String): CATO11 = CATO11(Some(str))
}
