package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class E25(value: Option[Boolean]) extends CtBoxIdentifier("Some of the income and gains may not be exempt or have not been applied for charitable or qualifying purposes only, and I have completed form CT600") with CtOptionalBoolean with Input

