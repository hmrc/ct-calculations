package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalBoolean, Input}

case class E45(value: Option[Boolean]) extends CtBoxIdentifier("during the period covered by these supplementary pages have you over claimed tax?") with CtOptionalBoolean with Input

