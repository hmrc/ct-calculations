package uk.gov.hmrc.ct.ct600e.v3

import uk.gov.hmrc.ct.box.{CtBoxIdentifier, CtOptionalInteger, Input}

case class E55(value: Option[Int]) extends CtBoxIdentifier("Income Investment income – excluding any amounts included on form CT600") with CtOptionalInteger with Input

