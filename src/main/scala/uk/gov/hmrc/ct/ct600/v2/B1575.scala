package uk.gov.hmrc.ct.ct600.v2

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalString, Input}


case class B1575(value: Option[String]) extends CtBoxIdentifier("Post Code") with CtOptionalString with Input

