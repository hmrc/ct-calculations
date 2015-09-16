package uk.gov.hmrc.ct.ct600.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class B860(value: Option[Int]) extends CtBoxIdentifier("Repayment amount upper bound") with CtOptionalInteger with Input
